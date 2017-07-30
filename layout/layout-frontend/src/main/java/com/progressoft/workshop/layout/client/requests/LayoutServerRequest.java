package com.progressoft.workshop.layout.client.requests;

import com.progressoft.brix.domino.api.client.request.ClientServerRequest;
import com.progressoft.workshop.layout.shared.response.LayoutResponse;
import com.progressoft.workshop.layout.shared.request.LayoutRequest;
import com.progressoft.workshop.layout.client.presenters.LayoutPresenter;
import com.progressoft.brix.domino.api.client.annotations.Request;
import com.progressoft.brix.domino.api.client.annotations.HandlerPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Request
@HandlerPath("LayoutRequest")
public class LayoutServerRequest extends ClientServerRequest<LayoutPresenter, LayoutRequest, LayoutResponse> {

    private static final Logger LOGGER= LoggerFactory.getLogger(LayoutServerRequest.class);

    @Override
    protected void process(LayoutPresenter presenter, LayoutRequest serverRequest, LayoutResponse response) {
        LOGGER.info("Message recieved from server : "+response.getServerMessage());
    }

    @Override
    public LayoutRequest buildArguments() {
        return new LayoutRequest("client message");
    }
}
