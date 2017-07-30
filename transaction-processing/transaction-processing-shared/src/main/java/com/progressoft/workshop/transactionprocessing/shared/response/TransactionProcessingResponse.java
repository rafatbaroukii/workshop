package com.progressoft.workshop.transactionprocessing.shared.response;

import com.progressoft.brix.domino.api.shared.request.ServerResponse;

public class TransactionProcessingResponse extends ServerResponse {

    private String serverMessage;

    public TransactionProcessingResponse() {
    }

    public TransactionProcessingResponse(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }
}
