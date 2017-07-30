package com.progressoft.workshop.upload.shared;

import java.util.Collection;

public class FileRecords {

    private Collection<FileRecord> files;

    public FileRecords() {
    }

    public FileRecords(Collection<FileRecord> files) {
        this.files = files;
    }

    public Collection<FileRecord> getFiles() {
        return files;
    }

    public void setFiles(Collection<FileRecord> files) {
        this.files = files;
    }
}
