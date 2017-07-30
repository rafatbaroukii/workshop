package com.progressoft.workshop.upload.server;

import com.progressoft.brix.domino.test.api.DominoTestServer;
import com.progressoft.workshop.upload.shared.FileRecord;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(VertxUnitRunner.class)
public class UploadTest {

    private static final String FILE_NAME = "test.txt";
    private static final String FILE_CONTENT = "file upload test data";

    private static final int DB_PORT = 27018;
    private static final String LOCALHOST = "localhost";

    private DefaultUploadHandlerSpy defaultUploadHandlerSpy;
    private Vertx vertx;
    private DominoTestServer dominoTestServer;

    @Rule
    public RunTestOnContext vertxRule = new RunTestOnContext();

    @Before
    public void setUp(TestContext context) {
        vertx = vertxRule.vertx();
        dominoTestServer = new DominoTestServer(vertx) {
            @Override
            public void onBeforeDominoLoad(Router router, JsonObject config) {
                defaultUploadHandlerSpy = new DefaultUploadHandlerSpy(this.getVertx());
                UploadContext.withUploadHandlerFactory(new UploadHandlerFactory() {
                    @Override
                    public Handler<RoutingContext> make(Vertx vertx) {
                        return defaultUploadHandlerSpy;
                    }
                });
            }
        };
        dominoTestServer.start(context);
    }

    @Test
    public void givenUploadModule_whenFileUploadRequestIsSent_thenTheEndPointShouldReceiveTheUploadedFile(TestContext context) throws Exception {
        sendUploadRequest(context.asyncAssertSuccess(handler -> {
            assertThat(defaultUploadHandlerSpy.getUploadName()).isEqualTo("name");
            assertThat(defaultUploadHandlerSpy.getFileName()).isEqualTo(FILE_NAME);
            assertThat(defaultUploadHandlerSpy.getUploadedContent()).isEqualTo("file upload test data");
        }));
    }

    @Test
    public void givenUploadModule_whenFileUploadRequestFailed_thenResponseWithErrorCodeShouldBeReturned(TestContext context) throws Exception {
        defaultUploadHandlerSpy.fail();
        sendUploadRequest(context.asyncAssertSuccess(response -> {
            assertThat(response.statusCode()).isEqualTo(500);
        }));
    }

    private void sendUploadRequest(Handler<AsyncResult<HttpResponse<Buffer>>> handler) {
        String boundary = "dLV9Wyq26L_-JQxk6ferf-RT153LhOO";
        Buffer buffer = buildUploadBody(boundary);
        WebClient.create(vertx).post(dominoTestServer.getActualPort(), "localhost", "/fileupload")
                .putHeader("content-type", "multipart/form-data; boundary=" + boundary)
                .putHeader("content-length", String.valueOf(buffer.length()))
                .sendBuffer(buffer, handler);
    }


    private Buffer buildUploadBody(String boundary) {
        String name = "name";
        String contentType = "application/octet-stream";
        String header =
                "--" + boundary + "\r\n" +
                        "Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + FILE_NAME + "\"\r\n" +
                        "Content-Type: " + contentType + "\r\n" +
                        "Content-Transfer-Encoding: binary\r\n" +
                        "\r\n";
        String footer = "\r\n--" + boundary + "--\r\n";
        return Buffer.buffer().appendString(header).appendString(FILE_CONTENT).appendString(footer);
    }

    @Test
    public void givenUploadFileRequestExecuted_whenUploadHandlerCompleted_thenFileShouldBePublishedToTheEventBus(TestContext context) throws Exception {
        Async async = context.async();
        vertx.eventBus().consumer("file-address", message -> {
            FileRecord fileRecord = Json.decodeValue(message.body().toString(), FileRecord.class);
            assertThat(fileRecord.getFileName()).isEqualTo(FILE_NAME);
            assertThat(fileRecord.getContent()).isEqualTo(FILE_CONTENT);
            async.complete();
        });
        sendUploadRequest(context.asyncAssertSuccess());
    }
}
