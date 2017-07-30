package com.progressoft.workshop.fileparser.shared;

public class InvalidTransaction {
    private String fileName;
    private String record;
    private String errorMessage;

    public InvalidTransaction() {
    }

    public InvalidTransaction(String fileName, String record, String errorMessage) {
        this.fileName = fileName;
        this.record = record;
        this.errorMessage = errorMessage;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
