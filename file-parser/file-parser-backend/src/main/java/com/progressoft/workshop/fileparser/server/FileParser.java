package com.progressoft.workshop.fileparser.server;

import com.progressoft.workshop.fileparser.shared.FileStatistics;
import com.progressoft.workshop.fileparser.shared.InvalidTransaction;
import com.progressoft.workshop.fileparser.shared.Transaction;
import com.progressoft.workshop.upload.shared.FileRecord;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;

public abstract class FileParser {

    public static class SupportedOperations {
        public static final String DEBIT = "D";
        public static final String CREDIT = "C";

        private static final Set<String> operations = new HashSet<>();

        static {
            operations.add(DEBIT);
            operations.add(CREDIT);
        }

        public static boolean contains(String operation) {
            return operations.contains(operation);
        }
    }

    public interface OnNextHandler {
        void handle(Transaction transaction);
    }

    public interface OnErrorHandler {
        void handle(InvalidTransaction invalidTransaction);
    }

    public interface OnCompleteHandler {
        void handle(FileStatistics statistics);
    }

    protected final FileRecord fileRecord;

    public FileParser(FileRecord fileRecord) {
        if (isNull(fileRecord))
            throw new InvalidFileRecordException();
        this.fileRecord = fileRecord;
    }

    abstract void parse();

    static class InvalidFileRecordException extends RuntimeException {
    }

    static class InvalidParsingHandlerException extends RuntimeException {
    }

    static class InvalidRecordException extends RuntimeException {

        public static final String UNSUPPORTED_OPERATION = "Unsupported transaction operation";
        public static final String WRONG_FORMAT = "Wrong format";
        private final String line;

        public InvalidRecordException(String line, String message) {
            super(message);
            this.line = line;
        }

        public String getLine() {
            return line;
        }
    }

    static class UnsupportedTransactionOperationException extends RuntimeException {
    }
}
