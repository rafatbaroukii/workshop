package com.progressoft.workshop.repository.server;

import com.google.auto.service.AutoService;
import com.progressoft.brix.domino.api.server.entrypoint.ServerAppEntryPoint;
import com.progressoft.brix.domino.api.server.entrypoint.ServerContext;
import com.progressoft.brix.domino.api.server.entrypoint.VertxContext;
import com.progressoft.workshop.fileparser.shared.FileStatistics;
import com.progressoft.workshop.fileparser.shared.FilesStats;
import com.progressoft.workshop.upload.shared.FileRecord;
import io.vertx.core.Future;
import io.vertx.core.json.Json;

import java.util.Collection;

@AutoService(ServerAppEntryPoint.class)
public class RepositoryEntryPoint implements ServerAppEntryPoint {
    @Override
    public void onModulesLoaded(ServerContext context) {
        VertxContext vertxContext = (VertxContext) context;
        RepositoriesContext.withVertx(vertxContext.vertx());
        RepositoriesContext.withFilesRepository(mongoFilesRepository(vertxContext));
        RepositoriesContext.withStatsRepository(mongoStatsRepository(vertxContext));

        vertxContext.vertx().eventBus().consumer("file-address", message -> {
            RepositoriesContext.filesRepository().save(Json.decodeValue(message.body().toString(), FileRecord.class)
                    , Future.future());
        });
        vertxContext.vertx().eventBus().consumer("stats-repository", message -> {
            RepositoriesContext.statsRepository().listAll(Future.<Collection<FileStatistics>>future().setHandler(files -> {
                message.reply(Json.encode(new FilesStats(files.result())));
            }));
        });
        vertxContext.vertx().eventBus().consumer("completed-stats", message -> {
            RepositoriesContext.statsRepository().save(Json.decodeValue(message.body().toString(), FileStatistics.class));
        });
    }

    private MongoFilesRepository mongoFilesRepository(VertxContext vertxContext) {
        return new MongoFilesRepository(getDBHost(vertxContext), getDBPort(vertxContext));
    }

    private MongoStatsRepository mongoStatsRepository(VertxContext vertxContext) {
        return new MongoStatsRepository(getDBHost(vertxContext), getDBPort(vertxContext));
    }

    private Integer getDBPort(VertxContext vertxContext) {
        return vertxContext.config().getInteger("db.port", 27018);
    }

    private String getDBHost(VertxContext vertxContext) {
        return vertxContext.config().getString("db.host", "localhost");
    }
}
