package com.progressoft.workshop.fileparser.server;

import com.progressoft.workshop.upload.shared.FileRecord;

public interface FileParserBuilderFactory {

    FileParserBuilderFactory CSV_BUILDER_FACTORY = new FileParserBuilderFactory() {
        @Override
        public FileParserBuilder<CsvFileParser> make(FileRecord fileRecord) {
            return new CsvFileParser.CsvFileParserBuilder(fileRecord);
        }
    };

    <T extends FileParser> FileParserBuilder<T> make(FileRecord fileRecord);
}
