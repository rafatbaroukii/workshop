package com.progressoft.workshop.upload.client;

import com.progressoft.workshop.fileparser.shared.FileStatistics;
import com.progressoft.workshop.upload.shared.BulkInValidTransactions;
import com.progressoft.workshop.upload.shared.BulkValidTransactions;

public class UploadClientContext {

    private static JsonMapper<BulkValidTransactions> validTransactionsObjectMapper;
    private static JsonMapper<BulkInValidTransactions> invalidTransactionsObjectMapper;
    private static JsonMapper<FileStatistics> fileStatsObjectMapper;

    public static JsonMapper<BulkValidTransactions> bulkValidTransactionsMapper() {
        return validTransactionsObjectMapper;
    }

    public static void withBulkValidTransactionsMapper(JsonMapper<BulkValidTransactions> mapper) {
        UploadClientContext.validTransactionsObjectMapper = mapper;
    }

    public static JsonMapper<BulkInValidTransactions> bulkInvalidTransactionsObjectMapper() {
        return invalidTransactionsObjectMapper;
    }

    public static void withInvalidTransactionsObjectMapper(JsonMapper<BulkInValidTransactions> mapper) {
        UploadClientContext.invalidTransactionsObjectMapper = mapper;
    }

    public static JsonMapper<FileStatistics> getFileStatsObjectMapper() {
        return fileStatsObjectMapper;
    }

    public static void setFileStatsObjectMapper(JsonMapper<FileStatistics> fileStatsObjectMapper) {
        UploadClientContext.fileStatsObjectMapper = fileStatsObjectMapper;
    }
}
