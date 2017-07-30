package com.progressoft.workshop.upload.server;

import io.vertx.core.Vertx;

public class UploadContext {

    private static UploadHandlerFactory factory = new UploadHandlerFactory() {
    };
    private static Vertx vertx;

    public static void withUploadHandlerFactory(UploadHandlerFactory factory) {
        UploadContext.factory = factory;
    }

    public static UploadHandlerFactory getUploadHandlerFactory() {
        return factory;
    }

    public static void withVertx(Vertx vertx){
        UploadContext.vertx =vertx;
    }

    public static Vertx vertx() {
        return vertx;
    }
}
