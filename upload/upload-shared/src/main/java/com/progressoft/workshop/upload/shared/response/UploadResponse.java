package com.progressoft.workshop.upload.shared.response;

import com.progressoft.brix.domino.api.shared.request.ServerResponse;

public class UploadResponse extends ServerResponse {

    private String serverMessage;

    public UploadResponse() {
    }

    public UploadResponse(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }
}
