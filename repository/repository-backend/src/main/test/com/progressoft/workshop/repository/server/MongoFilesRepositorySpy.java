package com.progressoft.workshop.repository.server;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.mongo.impl.MongoClientImpl;

public class MongoFilesRepositorySpy extends MongoFilesRepository {

    private boolean failOnSave;

    public MongoFilesRepositorySpy(String host, int dbPort, AsyncCompleted listener) {
        super(host, dbPort);
        listener.onComplete(client);
    }

    public MongoFilesRepositorySpy(String host, int dbPort) {
        super(host, dbPort);
    }

    @Override
    protected MongoClient createMongoClient(String host, int dbPort) {
        return new MongoClientImpl(RepositoriesContext.vertx(), new JsonObject().put("connection_string", "mongodb://" + host + ":" + dbPort), "") {
            @Override
            public MongoClient save(String collection, JsonObject document, Handler<AsyncResult<String>> resultHandler) {
                if (failOnSave) {
                    resultHandler.handle(Future.failedFuture("Failure for testing"));
                    return this;
                }
                return super.save(collection, document, resultHandler);
            }
        };
    }

    public void failOnSave() {
        this.failOnSave = true;
    }

    @FunctionalInterface
    public interface AsyncCompleted {
        void onComplete(MongoClient client);
    }
}
