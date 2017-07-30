package com.progressoft.workshop.transactionprocessing.server.handlers;

import com.progressoft.brix.domino.api.server.handler.Handler;
import com.progressoft.brix.domino.api.server.handler.RequestHandler;
import com.progressoft.workshop.transactionprocessing.shared.response.TransactionProcessingResponse;
import com.progressoft.workshop.transactionprocessing.shared.request.TransactionProcessingRequest;

import java.util.logging.Logger;

/**
 * Sample request class
 */
@Handler("TransactionProcessingRequest")
public class TransactionProcessingHandler implements RequestHandler<TransactionProcessingRequest, TransactionProcessingResponse> {
    private static final Logger LOGGER= Logger.getLogger(TransactionProcessingHandler.class.getName());
    @Override
    public TransactionProcessingResponse handleRequest(TransactionProcessingRequest request) {
        LOGGER.info("message recieved from client : "+request.getMessage());
        return new TransactionProcessingResponse("Server message");
    }
}
