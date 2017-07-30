package com.progressoft.workshop.layout.shared.request;

import com.progressoft.brix.domino.api.shared.request.ServerRequest;

public class LayoutRequest extends ServerRequest {

    private String message;

    public LayoutRequest() {
    }

    public LayoutRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
