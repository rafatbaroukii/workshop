package com.progressoft.workshop.repository.server;

import com.progressoft.workshop.fileparser.shared.FileStatistics;
import io.vertx.core.Future;

import java.util.Collection;

public interface StatsRepository {
    void save(FileStatistics fileStatistics);

    void listAll(Future<Collection<FileStatistics>> future);
}
