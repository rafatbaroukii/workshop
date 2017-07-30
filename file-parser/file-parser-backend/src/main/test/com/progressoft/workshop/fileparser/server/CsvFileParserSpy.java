package com.progressoft.workshop.fileparser.server;

import com.progressoft.workshop.upload.shared.FileRecord;

import static java.util.Objects.nonNull;

public class CsvFileParserSpy extends CsvFileParser {
    private final FileRecord fileRecord;
    private AsyncCompletedHandler asyncCompletedHandler;

    public CsvFileParserSpy(FileRecord fileRecord, OnNextHandler onNextHandler, OnErrorHandler onErrorHandler, OnCompleteHandler onCompleteHandler, FileAnalyzer.StatisticsListener statisticsListener) {
        super(fileRecord, onNextHandler, onErrorHandler, onCompleteHandler, statisticsListener);
        this.fileRecord = fileRecord;
    }

    @Override
    public void parse() {
        super.parse();
        if (nonNull(asyncCompletedHandler))
            this.asyncCompletedHandler.onComplete(fileRecord);
    }

    public CsvFileParserSpy onParsingCompleted(AsyncCompletedHandler handler) {
        this.asyncCompletedHandler = handler;
        return this;
    }


    @FunctionalInterface
    public interface AsyncCompletedHandler {
        void onComplete(FileRecord fileRecord);
    }
}
