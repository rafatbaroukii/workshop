package com.progressoft.workshop.repository.server;

import com.progressoft.workshop.upload.shared.FileRecord;
import io.vertx.core.Future;

import java.util.Collection;

public interface FilesRepository {
    void find(String fileName, Future<FileRecord> searchFuture);

    void save(FileRecord fileRecord, Future<FileRecord> saveFuture);

    void listAll(Future<Collection<FileRecord>> recordsFuture);
}
