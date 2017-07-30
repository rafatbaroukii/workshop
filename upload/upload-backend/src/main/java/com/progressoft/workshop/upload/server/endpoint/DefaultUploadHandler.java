package com.progressoft.workshop.upload.server.endpoint;

import com.progressoft.workshop.upload.shared.FileRecord;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

import java.util.Date;

public class DefaultUploadHandler implements Handler<RoutingContext> {

    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultUploadHandler.class);
    private static final int INTERNAL_SERVER_ERROR = 500;
    private final Vertx vertx;

    public DefaultUploadHandler(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void handle(RoutingContext routingContext) {
        routingContext.fileUploads().forEach(file -> readUploadedFile(routingContext, file));
    }

    protected FileSystem readUploadedFile(RoutingContext routingContext, FileUpload fileUpload) {
        return vertx.fileSystem().open(fileUpload.uploadedFileName(), new OpenOptions(), asyncResult -> {
            if (asyncResult.succeeded()) {
                Buffer buffer = Buffer.buffer();
                asyncResult.result().handler(buffer::appendBuffer).endHandler(event -> {
                    this.onCompleted(buffer.toString(), fileUpload.name(), fileUpload.fileName());
                    routingContext.response().end();
                });
            } else {
                LOGGER.error("Could not open file", asyncResult.cause());
                routingContext.response().setStatusCode(INTERNAL_SERVER_ERROR).end();
            }
        });
    }

    protected void onCompleted(String content, String uploadName, String fileName) {
        vertx.eventBus().publish("file-address", Json.encode(new FileRecord(fileName, content, new Date())));
    }
}
