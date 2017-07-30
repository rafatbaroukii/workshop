package com.progressoft.workshop.transactionprocessing.server;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

public interface TransactionHandlerFactory {

    default Handler<Message<String>> make() {
        return new DefaultTransactionProcessingHandler();
    }
}
