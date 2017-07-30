package com.progressoft.workshop.upload.shared;

import java.util.Date;

public class FileRecord {

    private String _id;
    private String fileName;
    private String content;
    private Date uploadDate;

    public FileRecord() {
    }

    public FileRecord(String fileName, String content, Date uploadDate) {
        this.fileName = fileName;
        this.content = content;
        this.uploadDate = uploadDate;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileRecord that = (FileRecord) o;

        return fileName.equals(that.fileName);
    }

    @Override
    public int hashCode() {
        return fileName.hashCode();
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
