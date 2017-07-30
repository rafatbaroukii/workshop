package com.progressoft.workshop.fileparser.server;

import com.progressoft.workshop.fileparser.shared.FileStatistics;
import com.progressoft.workshop.upload.shared.FileRecord;

import static com.progressoft.workshop.fileparser.server.FileParser.*;

public abstract class FileParserBuilder<T extends FileParser> {

    protected final FileRecord fileRecord;
    protected OnNextHandler onNextHandler = transaction -> {
    };
    protected OnErrorHandler onErrorHandler = invalidTransaction -> {
    };
    protected OnCompleteHandler onCompleteHandler = (FileStatistics statistics) -> {
    };
    protected FileAnalyzer.StatisticsListener statisticsListener = statistics -> {
    };

    protected FileParserBuilder(FileRecord fileRecord) {
        this.fileRecord = fileRecord;
    }

    public FileParserBuilder onNextTransaction(OnNextHandler onNextHandler) {
        this.onNextHandler = onNextHandler;
        return this;
    }

    public FileParserBuilder onParsingError(OnErrorHandler onErrorHandler) {
        this.onErrorHandler = onErrorHandler;
        return this;
    }

    public FileParserBuilder onParsingCompleted(OnCompleteHandler onCompleteHandler) {
        this.onCompleteHandler = onCompleteHandler;
        return this;
    }

    public FileParserBuilder onStatisticsUpdated(FileAnalyzer.StatisticsListener statisticsListener) {
        this.statisticsListener = statisticsListener;
        return this;
    }

    public abstract T build();
}
