package com.progressoft.workshop.fileparser.server;

import com.progressoft.workshop.fileparser.server.FileAnalyzer.StatisticsListener;
import com.progressoft.workshop.fileparser.shared.FileStatistics;
import com.progressoft.workshop.fileparser.shared.InvalidTransaction;
import com.progressoft.workshop.fileparser.shared.Transaction;
import com.progressoft.workshop.upload.shared.FileRecord;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.parsetools.RecordParser;

public class CsvFileParser extends FileParser {

    private final WorkerExecutor executor;
    private final OnNextHandler onNextHandler;
    private final OnErrorHandler onErrorHandler;
    private final OnCompleteHandler onCompleteHandler;

    private FileAnalyzer analyzer;

    protected CsvFileParser(FileRecord fileRecord, OnNextHandler onNextHandler, OnErrorHandler onErrorHandler, OnCompleteHandler onCompleteHandler, StatisticsListener statisticsListener) {
        super(fileRecord);
        this.onNextHandler = onNextHandler;
        this.onErrorHandler = onErrorHandler;
        this.onCompleteHandler = onCompleteHandler;
        this.analyzer = new FileAnalyzer(fileRecord.getFileName(), fileRecord.getUploadDate(), statisticsListener);
        executor = FileParserContext.vertx().createSharedWorkerExecutor("file-parser-pool");
    }

    @Override
    public void parse() {
        executeBlockingIgnoreOrder(event -> {
            RecordParser.newDelimited("\n", lineBuffer -> {
                try {
                    onValidTransaction(makeTransaction(fileRecord.getFileName(), lineBuffer.toString()));
                } catch (InvalidRecordException e) {
                    onFailed(e);
                }
            }).handle(Buffer.buffer(fileRecord.getContent()));
            FileStatistics statistics = analyzer.parseCompleted();
            onCompleteHandler.handle(statistics);
        });
    }

    private Transaction makeTransaction(String fileName, String line) {
        String[] values = line.split(",");
        if (values.length != 3)
            throw new CsvFileParser.InvalidRecordException(line, InvalidRecordException.WRONG_FORMAT);
        if (!SupportedOperations.contains(values[1]))
            throw new CsvFileParser.InvalidRecordException(line, InvalidRecordException.UNSUPPORTED_OPERATION);
        return new Transaction(values[0], values[1], Double.parseDouble(values[2]), fileName);
    }

    private void onFailed(InvalidRecordException e) {
        analyzer.updateInvalid();
        onErrorHandler.handle(new InvalidTransaction(fileRecord.getFileName(), e.getLine(), e.getMessage()));
    }

    private void onValidTransaction(Transaction transaction) {
        analyzer.update(transaction);
        onNextHandler.handle(transaction);
    }

    private void executeBlockingIgnoreOrder(Handler<Future<Object>> blockingCodeHandler) {
        executor.executeBlocking(blockingCodeHandler, false, event -> {
        });
    }

    public static class CsvFileParserBuilder extends FileParserBuilder<CsvFileParser> {

        public CsvFileParserBuilder(FileRecord fileRecord) {
            super(fileRecord);
        }

        @Override
        public CsvFileParser build() {
            return new CsvFileParser(fileRecord, onNextHandler, onErrorHandler, onCompleteHandler, statisticsListener);
        }
    }
}