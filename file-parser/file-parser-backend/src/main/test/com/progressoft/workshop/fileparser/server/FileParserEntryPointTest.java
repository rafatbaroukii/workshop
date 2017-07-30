package com.progressoft.workshop.fileparser.server;

import com.progressoft.brix.domino.test.api.DominoTestServer;
import com.progressoft.workshop.fileparser.shared.InvalidTransaction;
import com.progressoft.workshop.fileparser.shared.Transaction;
import com.progressoft.workshop.upload.shared.FileRecord;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static com.progressoft.workshop.fileparser.server.FileParser.InvalidRecordException;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(VertxUnitRunner.class)
public class FileParserEntryPointTest {

    @Rule
    public RunTestOnContext vertxRule = new RunTestOnContext();
    private Vertx vertx;
    private CsvFileParserSpy parserSpy;
    private CsvFileParserSpy.AsyncCompletedHandler completeListener = fileRecord -> {
    };

    @Before
    public void setUp(TestContext context) throws Exception {
        vertx = vertxRule.vertx();
        FileParserContext.setBuilderFactory(new FileParserBuilderFactory() {
            @Override
            public CsvFileParserSpyBuilder make(FileRecord fileRecord) {
                return new CsvFileParserSpyBuilder(fileRecord) {
                    @Override
                    public CsvFileParser build() {
                        parserSpy = (CsvFileParserSpy) super.build();
                        parserSpy.onParsingCompleted(completeListener);
                        return parserSpy;
                    }
                };
            }
        });
        new DominoTestServer(vertx).start(context);
    }

    @Test
    public void givenFileParserModule_whenStartEntryPoint_thenShouldListenToTheEventBus(TestContext context) throws Exception {
        Async async = context.async();
        FileRecord fileRecord = new FileRecord("test", "testData", new Date());
        completeListener = record -> {
            assertThat(record.getFileName()).isEqualTo(fileRecord.getFileName());
            async.complete();
        };
        vertx.eventBus().publish("file-address", Json.encode(fileRecord));
    }

    @Test
    public void givenFileParserModule_whenFileIsParsed_thenShouldPublishRecordsToTheEventBus(TestContext context) throws Exception {
        Async async = context.async();
        vertx.eventBus().consumer("valid-transactions", handler -> {
            Transaction transaction = Json.decodeValue(handler.body().toString(), Transaction.class);
            assertThat(transaction.getAccountNumber()).isEqualTo("11111");
            assertThat(transaction.getOperation()).isEqualTo("D");
            assertThat(transaction.getAmount()).isEqualTo(35.32);
            async.complete();
        });
        FileRecord fileRecord = new FileRecord("test", "11111,D,35.32\n", new Date());
        vertx.eventBus().publish("file-address", Json.encode(fileRecord));
    }

    @Test
    public void givenFileParserModule_whenFileHasInvalidRecords_thenTheseInvalidRecordsShouldBePublishedToTheEventBus(TestContext context) throws Exception {
        Async async = context.async();
        vertx.eventBus().consumer("invalid-transactions", handler -> {
            InvalidTransaction invalidTransaction = Json.decodeValue(handler.body().toString(), InvalidTransaction.class);
            assertThat(invalidTransaction.getRecord()).isEqualTo("invalid-record");
            assertThat(invalidTransaction.getErrorMessage()).isEqualTo(InvalidRecordException.WRONG_FORMAT);
            async.complete();
        });
        vertx.eventBus().publish("file-address", Json.encode(new FileRecord("test", "invalid-record\n", new Date())));
    }

    @Test
    public void givenFileParserModule_whenFileHasRecordWithUnsupportedOperation_thenThisInvalidRecordShouldBePublishedToTheEventBus(TestContext context) throws Exception {
        Async async = context.async();
        vertx.eventBus().consumer("invalid-transactions", handler -> {
            InvalidTransaction invalidTransaction = Json.decodeValue(handler.body().toString(), InvalidTransaction.class);
            assertThat(invalidTransaction.getRecord()).isEqualTo("11111,unsupported-type,20.5");
            assertThat(invalidTransaction.getErrorMessage()).isEqualTo(InvalidRecordException.UNSUPPORTED_OPERATION);
        });
        async.complete();
        FileRecord fileRecord = new FileRecord("test", "11111,unsupported-type,20.5\n", new Date());
        vertx.eventBus().publish("file-address", Json.encode(fileRecord));
    }
}
