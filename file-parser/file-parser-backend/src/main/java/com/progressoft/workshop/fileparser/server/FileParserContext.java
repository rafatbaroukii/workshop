package com.progressoft.workshop.fileparser.server;

import io.vertx.core.Vertx;

public class FileParserContext {
    private static Vertx vertx;
    private static FileParserBuilderFactory builderFactory = FileParserBuilderFactory.CSV_BUILDER_FACTORY;

    public static void withVertx(Vertx vertx) {
        FileParserContext.vertx = vertx;
    }

    public static Vertx vertx() {
        return vertx;
    }

    public static FileParserBuilderFactory getBuilderFactory() {
        return builderFactory;
    }

    public static void setBuilderFactory(FileParserBuilderFactory builderFactory) {
        FileParserContext.builderFactory = builderFactory;
    }
}
