package com.progressoft.workshop.uploadedfiles.server.handlers;

import com.progressoft.brix.domino.api.server.handler.CallbackRequestHandler;
import com.progressoft.brix.domino.api.server.handler.Handler;
import com.progressoft.workshop.fileparser.shared.FilesStats;
import com.progressoft.workshop.uploadedfiles.server.UploadedFilesContext;
import com.progressoft.workshop.uploadedfiles.shared.request.UploadedFilesRequest;
import com.progressoft.workshop.uploadedfiles.shared.response.UploadedFilesResponse;
import io.vertx.core.json.Json;

import java.util.logging.Logger;

/**
 * Sample request class
 */
@Handler("UploadedFilesRequest")
public class UploadedFilesHandler implements CallbackRequestHandler<UploadedFilesRequest, UploadedFilesResponse> {
    private static final Logger LOGGER = Logger.getLogger(UploadedFilesHandler.class.getName());

    @Override
    public void handleRequest(UploadedFilesRequest request, ResponseCallback<UploadedFilesResponse> responseCallback) {
        UploadedFilesContext.vertx().eventBus().send("stats-repository", "list-all", reply -> {
            FilesStats filesStats = Json.decodeValue(reply.result().body().toString(), FilesStats.class);
            responseCallback.complete(new UploadedFilesResponse(filesStats));
        });
    }
}
