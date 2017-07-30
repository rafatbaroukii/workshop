package com.progressoft.workshop.uploadedfiles.server;

import io.vertx.core.Vertx;

public class UploadedFilesContext {
    private static Vertx vertx;

    public static void withVertx(Vertx vertx) {
        UploadedFilesContext.vertx = vertx;
    }

    public static Vertx vertx() {
        return vertx;
    }
}
