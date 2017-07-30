package com.progressoft.workshop.transactionprocessing.client.requests;

import com.progressoft.brix.domino.api.client.request.ClientServerRequest;
import com.progressoft.workshop.transactionprocessing.shared.response.TransactionProcessingResponse;
import com.progressoft.workshop.transactionprocessing.shared.request.TransactionProcessingRequest;
import com.progressoft.workshop.transactionprocessing.client.presenters.TransactionProcessingPresenter;
import com.progressoft.brix.domino.api.client.annotations.Request;
import com.progressoft.brix.domino.api.client.annotations.HandlerPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Request
@HandlerPath("TransactionProcessingRequest")
public class TransactionProcessingServerRequest extends ClientServerRequest<TransactionProcessingPresenter, TransactionProcessingRequest, TransactionProcessingResponse> {

    private static final Logger LOGGER= LoggerFactory.getLogger(TransactionProcessingServerRequest.class);

    @Override
    protected void process(TransactionProcessingPresenter presenter, TransactionProcessingRequest serverRequest, TransactionProcessingResponse response) {
        LOGGER.info("Message recieved from server : "+response.getServerMessage());
    }

    @Override
    public TransactionProcessingRequest buildArguments() {
        return new TransactionProcessingRequest("client message");
    }
}
