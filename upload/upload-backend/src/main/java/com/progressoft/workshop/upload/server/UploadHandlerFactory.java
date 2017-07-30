package com.progressoft.workshop.upload.server;

import com.progressoft.workshop.upload.server.endpoint.DefaultUploadHandler;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

public interface UploadHandlerFactory {

    default Handler<RoutingContext> make(Vertx vertx) {
        return new DefaultUploadHandler(vertx);
    }
}
