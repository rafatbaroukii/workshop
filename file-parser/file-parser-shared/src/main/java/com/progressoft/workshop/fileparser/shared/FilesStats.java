package com.progressoft.workshop.fileparser.shared;

import java.util.Collection;

public class FilesStats {

    public Collection<FileStatistics> statistics;

    public FilesStats() {
    }

    public FilesStats(Collection<FileStatistics> statistics) {
        this.statistics = statistics;
    }
}
