package com.progressoft.workshop.upload.client.requests;

import com.progressoft.brix.domino.api.client.request.ClientServerRequest;
import com.progressoft.workshop.upload.shared.response.UploadResponse;
import com.progressoft.workshop.upload.shared.request.UploadRequest;
import com.progressoft.workshop.upload.client.presenters.UploadPresenter;
import com.progressoft.brix.domino.api.client.annotations.Request;
import com.progressoft.brix.domino.api.client.annotations.HandlerPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Request
@HandlerPath("UploadRequest")
public class UploadServerRequest extends ClientServerRequest<UploadPresenter, UploadRequest, UploadResponse> {

    private static final Logger LOGGER= LoggerFactory.getLogger(UploadServerRequest.class);

    @Override
    protected void process(UploadPresenter presenter, UploadRequest serverRequest, UploadResponse response) {
        LOGGER.info("Message recieved from server : "+response.getServerMessage());
    }

    @Override
    public UploadRequest buildArguments() {
        return new UploadRequest("client message");
    }
}
