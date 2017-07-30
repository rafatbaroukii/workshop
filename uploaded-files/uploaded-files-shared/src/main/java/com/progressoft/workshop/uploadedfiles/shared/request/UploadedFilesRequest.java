package com.progressoft.workshop.uploadedfiles.shared.request;

import com.progressoft.brix.domino.api.shared.request.ServerRequest;

public class UploadedFilesRequest extends ServerRequest {

    private String message;

    public UploadedFilesRequest() {
    }

    public UploadedFilesRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
