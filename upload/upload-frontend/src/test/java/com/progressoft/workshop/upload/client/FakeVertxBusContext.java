package com.progressoft.workshop.upload.client;

import com.progressoft.brix.domino.vertxbus.shared.extension.VertxBusContext;

import java.util.*;

public class FakeVertxBusContext implements VertxBusContext {

    private Map<String, List<EventBusMessageHandler>> messageHandlers = new HashMap<>();

    @Override
    public <T> void registerMessageHandler(String address, EventBusMessageHandler<T> handler) {
        if (messageHandlers.containsKey(address))
            messageHandlers.get(address).add(handler);
        else
            messageHandlers.put(address, new ArrayList<>(Collections.singletonList(handler)));
    }

    @Override
    public void registerEventBusCloseHandler(EventBusCloseHandler handler) {

    }

    public List<EventBusMessageHandler> getListeners(String address) {
        if (messageHandlers.containsKey(address))
            return messageHandlers.get(address);
        return new ArrayList<>();
    }

    public void publishMessage(String address, String message) {
        messageHandlers.get(address).forEach(h -> h.handle(message));
    }
}
