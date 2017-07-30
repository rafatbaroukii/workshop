package com.progressoft.workshop.layout.shared.response;

import com.progressoft.brix.domino.api.shared.request.ServerResponse;

public class LayoutResponse extends ServerResponse {

    private String serverMessage;

    public LayoutResponse() {
    }

    public LayoutResponse(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }
}
