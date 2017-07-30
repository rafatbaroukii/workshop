package com.progressoft.workshop.upload.server.handlers;

import com.progressoft.brix.domino.api.server.handler.Handler;
import com.progressoft.brix.domino.api.server.handler.RequestHandler;
import com.progressoft.workshop.upload.shared.response.UploadResponse;
import com.progressoft.workshop.upload.shared.request.UploadRequest;

import java.util.logging.Logger;

/**
 * Sample request class
 */
@Handler("UploadRequest")
public class UploadHandler implements RequestHandler<UploadRequest, UploadResponse> {
    private static final Logger LOGGER= Logger.getLogger(UploadHandler.class.getName());
    @Override
    public UploadResponse handleRequest(UploadRequest request) {
        LOGGER.info("message recieved from client : "+request.getMessage());
        return new UploadResponse("Server message");
    }
}
