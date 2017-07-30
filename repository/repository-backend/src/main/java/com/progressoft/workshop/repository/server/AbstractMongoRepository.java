package com.progressoft.workshop.repository.server;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import static java.util.Objects.isNull;

public abstract class AbstractMongoRepository {

    private final String host;
    private final int dbPort;
    protected final MongoClient client;

    public AbstractMongoRepository(String host, int dbPort) {
        if (isNull(host) || host.trim().isEmpty())
            throw new InvalidHostException();
        if (dbPort < 0)
            throw new InvalidPortException();
        this.host = host;
        this.dbPort = dbPort;

        client = createMongoClient(host, dbPort);
    }

    protected MongoClient createMongoClient(String host, int dbPort) {
        return MongoClient.createNonShared(RepositoriesContext.vertx(), new JsonObject()
                .put("connection_string", "mongodb://" + host + ":" + dbPort));
    }

    public class InvalidHostException extends RuntimeException {
    }

    public class InvalidPortException extends RuntimeException {
    }
}
