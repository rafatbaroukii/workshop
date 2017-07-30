package com.progressoft.workshop.layout.server.handlers;

import com.progressoft.brix.domino.api.server.handler.Handler;
import com.progressoft.brix.domino.api.server.handler.RequestHandler;
import com.progressoft.workshop.layout.shared.response.LayoutResponse;
import com.progressoft.workshop.layout.shared.request.LayoutRequest;

import java.util.logging.Logger;

/**
 * Sample request class
 */
@Handler("LayoutRequest")
public class LayoutHandler implements RequestHandler<LayoutRequest, LayoutResponse> {
    private static final Logger LOGGER= Logger.getLogger(LayoutHandler.class.getName());
    @Override
    public LayoutResponse handleRequest(LayoutRequest request) {
        LOGGER.info("message recieved from client : "+request.getMessage());
        return new LayoutResponse("Server message");
    }
}
