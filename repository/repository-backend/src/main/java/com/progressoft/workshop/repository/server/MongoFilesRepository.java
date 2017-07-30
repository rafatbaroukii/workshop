package com.progressoft.workshop.repository.server;

import com.progressoft.workshop.upload.shared.FileRecord;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import java.util.Collection;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

public class MongoFilesRepository extends AbstractMongoRepository implements FilesRepository {

    public static final String UPLOADED_FILES = "uploaded-files";

    public MongoFilesRepository(String host, int dbPort) {
        super(host, dbPort);
    }

    @Override
    public void find(String fileName, Future<FileRecord> searchFuture) {
        client.findOne(UPLOADED_FILES, new JsonObject().put("fileName", fileName), new JsonObject()
                .put("_id", false), event -> {
            if (event.succeeded())
                completeFileRecord(searchFuture, event);
            else
                searchFuture.fail(event.cause());
        });
    }

    private void completeFileRecord(Future<FileRecord> searchFuture, AsyncResult<JsonObject> event) {
        if (isNull(event.result()))
            searchFuture.complete(null);
        else
            searchFuture.complete(Json.decodeValue(event.result().toString(), FileRecord.class));
    }

    @Override
    public void save(FileRecord fileRecord, Future<FileRecord> saveFuture) {
        fileRecord.set_id(UUID.randomUUID().toString());
        client.save(UPLOADED_FILES, new JsonObject(Json.encode(fileRecord)), event -> {
            if (event.succeeded())
                saveFuture.complete(fileRecord);
            else
                saveFuture.fail(event.cause());
        });
    }


    @Override
    public void listAll(Future<Collection<FileRecord>> recordsFuture) {
        client.find(UPLOADED_FILES, new JsonObject(), event -> {
            recordsFuture.complete(event.result().stream().map(this::asFileRecord).collect(toList()));
        });
    }

    private FileRecord asFileRecord(JsonObject j) {
        return Json.decodeValue(j.toString(), FileRecord.class);
    }
}
