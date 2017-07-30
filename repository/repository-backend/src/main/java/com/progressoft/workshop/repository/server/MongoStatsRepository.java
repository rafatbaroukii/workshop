package com.progressoft.workshop.repository.server;

import com.progressoft.workshop.fileparser.shared.FileStatistics;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import java.util.Collection;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class MongoStatsRepository extends AbstractMongoRepository implements StatsRepository {

    public static final String FILE_STATS = "file-stats";

    public MongoStatsRepository(String host, int dbPort) {
        super(host, dbPort);
    }

    @Override
    public void listAll(Future<Collection<FileStatistics>> future) {
        client.find(FILE_STATS, new JsonObject(), event -> {
            future.complete(event.result().stream().map(this::asFileStats).collect(toList()));
        });
    }

    private FileStatistics asFileStats(JsonObject j) {
        return Json.decodeValue(j.toString(), FileStatistics.class);
    }

    @Override
    public void save(FileStatistics fileStatistics) {
        fileStatistics.set_id(UUID.randomUUID().toString());
        client.save(FILE_STATS, new JsonObject(Json.encode(fileStatistics)), event -> {
        });
    }
}
