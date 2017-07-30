package com.progressoft.workshop.transactionprocessing.server;

import com.google.auto.service.AutoService;
import com.progressoft.brix.domino.api.server.entrypoint.ServerAppEntryPoint;
import com.progressoft.brix.domino.api.server.entrypoint.ServerContext;
import com.progressoft.brix.domino.api.server.entrypoint.VertxContext;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

@AutoService(ServerAppEntryPoint.class)
public class TransactionProcessingEntryPoint implements ServerAppEntryPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProcessingEntryPoint.class);

    @Override
    public void onModulesLoaded(ServerContext context) {
        try {
            VertxContext vertxContext = context.cast(VertxContext.class);
            vertxContext.vertx().eventBus().consumer("valid-transactions",
                    TransactionProcessingContext.transactionHandlerFactory().make());
        } catch (ServerContext.InvalidContextTypeException e) {
            LOGGER.error("Not a vertx application", e);
        }
    }
}
