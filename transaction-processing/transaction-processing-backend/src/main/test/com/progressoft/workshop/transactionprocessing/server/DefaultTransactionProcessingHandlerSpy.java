package com.progressoft.workshop.transactionprocessing.server;

import com.progressoft.workshop.fileparser.shared.Transaction;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;

import static java.util.Objects.nonNull;

public class DefaultTransactionProcessingHandlerSpy extends DefaultTransactionProcessingHandler {
    private AsyncCompletedListener listener;
    private ErrorListener errorListener;

    @Override
    public void handle(Message<String> message) {
        try {
            super.handle(message);
            if (nonNull(listener))
                listener.onCompleted(Json.decodeValue(message.body(), Transaction.class));
        } catch (Exception e) {
            if (nonNull(errorListener))
                errorListener.onError(e);
        }
    }

    public void onAsyncCompleted(AsyncCompletedListener listener) {
        this.listener = listener;
    }

    public void onError(ErrorListener errorListener) {
        this.errorListener = errorListener;
    }

    @FunctionalInterface
    public interface AsyncCompletedListener {
        void onCompleted(Transaction transaction);
    }

    @FunctionalInterface
    public interface ErrorListener {
        void onError(Throwable error);
    }
}
