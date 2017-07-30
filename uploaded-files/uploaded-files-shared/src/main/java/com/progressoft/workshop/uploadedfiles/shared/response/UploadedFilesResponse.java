package com.progressoft.workshop.uploadedfiles.shared.response;

import com.progressoft.brix.domino.api.shared.request.ServerResponse;
import com.progressoft.workshop.fileparser.shared.FilesStats;

public class UploadedFilesResponse extends ServerResponse {

    public FilesStats filesStats = new FilesStats();

    public UploadedFilesResponse() {
    }

    public UploadedFilesResponse(FilesStats filesStats) {
        this.filesStats = filesStats;
    }
}
