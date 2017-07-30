package com.progressoft.workshop.repository.server;

import com.progressoft.workshop.upload.shared.FileRecord;
import io.vertx.core.Future;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryFilesRepository implements FilesRepository {

    private Map<String, FileRecord> records = new HashMap<>();

    @Override
    public void save(FileRecord fileRecord, Future<FileRecord> saveFuture) {
        records.put(fileRecord.getFileName(), fileRecord);
        saveFuture.complete(fileRecord);
    }

    @Override
    public void find(String fileName, Future<FileRecord> searchFuture) {
        if (records.containsKey(fileName))
            searchFuture.complete(records.get(fileName));
        else
            searchFuture.fail("not found record");
    }

    @Override
    public void listAll(Future<Collection<FileRecord>> recordsFuture) {

    }
}
