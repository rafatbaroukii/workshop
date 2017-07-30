package com.progressoft.workshop.repository.server;

import io.vertx.core.Vertx;

public class RepositoriesContext {
    private static Vertx vertx;
    private static FilesRepository filesRepository;
    private static StatsRepository statsRepository;

    public static Vertx vertx() {
        return vertx;
    }

    public static void withVertx(Vertx vertx) {
        RepositoriesContext.vertx = vertx;
    }

    public static void withFilesRepository(FilesRepository filesRepository) {
        RepositoriesContext.filesRepository = filesRepository;
    }

    public static FilesRepository filesRepository() {
        return filesRepository;
    }

    public static StatsRepository statsRepository() {
        return statsRepository;
    }

    public static void withStatsRepository(StatsRepository statsRepository) {
        RepositoriesContext.statsRepository = statsRepository;
    }
}
