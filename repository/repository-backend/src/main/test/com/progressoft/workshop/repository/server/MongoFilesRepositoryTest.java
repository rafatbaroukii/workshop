package com.progressoft.workshop.repository.server;

import com.progressoft.workshop.upload.shared.FileRecord;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static com.progressoft.workshop.repository.server.MongoFilesRepository.UPLOADED_FILES;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class MongoFilesRepositoryTest {

    private static final int DB_PORT = 27018;
    private static final String LOCALHOST = "localhost";

    private static MongodExecutable mongodExe;

    @RunWith(Parameterized.class)
    public static class RepositoryHostCreationTest {
        @Parameterized.Parameter
        public String host;

        @BeforeClass
        public static void beforeClass() throws IOException {
            startFakeMongo();
        }

        @AfterClass
        public static void afterClass() {
            stopMongo();
        }

        @Parameterized.Parameters(name = "{index}: createWithHost(\"{0}\")")
        public static Collection<String> parametersConnection() {
            return Arrays.asList(null, "", "    ");
        }

        @Test(expected = MongoFilesRepository.InvalidHostException.class)
        public void createFilesRepositoryWithInvalidHostShouldThrowException() throws Exception {
            new MongoFilesRepository(host, DB_PORT);
        }
    }


    @RunWith(VertxUnitRunner.class)
    public static class RepositoryTest {

        @Rule
        public RunTestOnContext rule = new RunTestOnContext();
        private Vertx vertx;
        private FileRecord testFile;

        @BeforeClass
        public static void beforeClass() throws IOException {
            startFakeMongo();
        }

        @AfterClass
        public static void afterClass() {
            stopMongo();
        }

        @Before
        public void setUp() throws Exception {
            vertx = rule.vertx();
            RepositoriesContext.withVertx(vertx);
            testFile = new FileRecord("testFile", "Test Content", new Date());
        }

        @Test(expected = MongoFilesRepository.InvalidPortException.class)
        public void createFilesRepositoryWithInvalidPort_thenShouldThrowException() throws Exception {
            new MongoFilesRepository(LOCALHOST, -1);
        }

        @Test
        public void canCreateFilesRepositoryWithHostAndPort() throws Exception {
            new MongoFilesRepository(LOCALHOST, DB_PORT);
        }

        @Test
        public void givenMongoFilesRepositoryCreated_thenUploadedeFilesCollectionShouldBeCreated(TestContext context) throws Exception {
            Async async = context.async();
            new MongoFilesRepositorySpy(LOCALHOST, DB_PORT, client -> {
                assertThat(client).isNotNull();
                async.complete();
            });
        }

        @Test
        public void givenMongoFileRepository_whenSavingAFileRecord_thenFileRecordShouldBePersisted(TestContext context) throws Exception {
            Async async = context.async();
            MongoFilesRepositorySpy mongoFilesRepositorySpy = repository();
            mongoFilesRepositorySpy.save(testFile, Future.future(fileRecord ->
                    mongoFilesRepositorySpy.client
                            .findOne(UPLOADED_FILES, new JsonObject().put("fileName", "testFile"), new JsonObject(), element -> {
                                assertThat(element.result().getString("fileName")).isEqualTo("testFile");
                                async.complete();
                            })
            ));
        }

        @Test
        public void givenMongoFileRepository_whenFailedToSaveFileRecord_thenShouldCallFailedHandler(TestContext context) throws Exception {
            Async async = context.async();
            MongoFilesRepositorySpy repository = repository();
            repository.failOnSave();
            repository.save(testFile, Future.<FileRecord>future().setHandler(event -> {
                assertThat(event.failed()).isTrue();
                async.complete();
            }));
        }


        @Test
        public void givenMongoFileRepository_whenFindingAnExistItem_thenShouldGetTheItemBySearchResult(TestContext context) throws Exception {
            Async async = context.async();
            MongoFilesRepositorySpy repository = repository();
            repository.save(testFile, Future.<FileRecord>future().setHandler(fileRecord -> {
                repository.find(testFile.getFileName(), Future.<FileRecord>future().setHandler(record -> {
                    assertThat(record.succeeded()).isTrue();
                    assertThat(record.result()).isEqualTo(testFile);
                    async.complete();
                }));
            }));
        }

        @Test
        public void givenMongoFileRepository_whenFindingNonExistItem_thenShouldProvideNullRecord(TestContext context) throws Exception {
            Async async = context.async();
            MongoFilesRepositorySpy repository = repository();
            repository.find("not-found-file", Future.<FileRecord>future().setHandler(record -> {
                assertThat(record.result()).isNull();
                async.complete();
            }));
        }

    }

    static MongoFilesRepositorySpy repository() {
        return new MongoFilesRepositorySpy(LOCALHOST, DB_PORT);
    }

    private static void stopMongo() {
        if (mongodExe != null) {
            mongodExe.stop();
        }
    }

    private static void startFakeMongo() throws IOException {
        mongodExe = MongodStarter.getDefaultInstance().prepare(new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(LOCALHOST, DB_PORT, Network.localhostIsIPv6()))
                .build());
        mongodExe.start();
    }
}
