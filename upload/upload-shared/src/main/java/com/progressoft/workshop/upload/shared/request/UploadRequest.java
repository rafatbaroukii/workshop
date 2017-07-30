package com.progressoft.workshop.upload.shared.request;

import com.progressoft.brix.domino.api.shared.request.ServerRequest;

public class UploadRequest extends ServerRequest {

    private String message;

    public UploadRequest() {
    }

    public UploadRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
