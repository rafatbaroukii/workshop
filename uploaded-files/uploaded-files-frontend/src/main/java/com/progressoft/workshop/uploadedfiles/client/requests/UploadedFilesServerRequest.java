package com.progressoft.workshop.uploadedfiles.client.requests;

import com.progressoft.brix.domino.api.client.request.ClientServerRequest;
import com.progressoft.workshop.uploadedfiles.shared.response.UploadedFilesResponse;
import com.progressoft.workshop.uploadedfiles.shared.request.UploadedFilesRequest;
import com.progressoft.workshop.uploadedfiles.client.presenters.UploadedFilesPresenter;
import com.progressoft.brix.domino.api.client.annotations.Request;
import com.progressoft.brix.domino.api.client.annotations.HandlerPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Request
@HandlerPath("UploadedFilesRequest")
public class UploadedFilesServerRequest extends ClientServerRequest<UploadedFilesPresenter, UploadedFilesRequest, UploadedFilesResponse> {

    private static final Logger LOGGER= LoggerFactory.getLogger(UploadedFilesServerRequest.class);

    @Override
    protected void process(UploadedFilesPresenter presenter, UploadedFilesRequest serverRequest, UploadedFilesResponse response) {
        presenter.onFilesLoaded(response.filesStats);
    }

    @Override
    public UploadedFilesRequest buildArguments() {
        return new UploadedFilesRequest("client message");
    }
}
