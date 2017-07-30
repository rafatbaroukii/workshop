package com.progressoft.workshop.fileparser.server;

import com.progressoft.workshop.upload.shared.FileRecord;

public class CsvFileParserSpyBuilder extends CsvFileParser.CsvFileParserBuilder {
    public CsvFileParserSpyBuilder(FileRecord fileRecord) {
        super(fileRecord);
    }

    @Override
    public CsvFileParser build() {
        return new CsvFileParserSpy(fileRecord, onNextHandler, onErrorHandler, onCompleteHandler, statisticsListener);
    }
}
