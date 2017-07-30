package com.progressoft.workshop.transactionprocessing.shared.request;

import com.progressoft.brix.domino.api.shared.request.ServerRequest;

public class TransactionProcessingRequest extends ServerRequest {

    private String message;

    public TransactionProcessingRequest() {
    }

    public TransactionProcessingRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
