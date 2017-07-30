package com.progressoft.workshop.uploadedfiles.shared.response;

import java.util.Date;

public class FileRecordInfo {

    public String fileName;
    public Date uploadDate;

    public FileRecordInfo() {
    }

    public FileRecordInfo(String fileName, Date uploadDate) {
        this.fileName = fileName;
        this.uploadDate = uploadDate;
    }
}
