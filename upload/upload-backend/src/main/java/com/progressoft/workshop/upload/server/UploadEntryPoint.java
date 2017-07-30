package com.progressoft.workshop.upload.server;

import com.google.auto.service.AutoService;
import com.progressoft.brix.domino.api.server.entrypoint.ServerAppEntryPoint;
import com.progressoft.brix.domino.api.server.entrypoint.ServerContext;
import com.progressoft.brix.domino.api.server.entrypoint.VertxContext;
import com.progressoft.workshop.fileparser.shared.InvalidTransaction;
import com.progressoft.workshop.fileparser.shared.Transaction;
import com.progressoft.workshop.upload.shared.BulkInValidTransactions;
import com.progressoft.workshop.upload.shared.BulkValidTransactions;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

@AutoService(ServerAppEntryPoint.class)
public class UploadEntryPoint implements ServerAppEntryPoint {

    private final static Logger LOGGER = LoggerFactory.getLogger(UploadEntryPoint.class);

    private BulkValidTransactions validTransactions = new BulkValidTransactions();
    private BulkInValidTransactions inValidTransactions = new BulkInValidTransactions();

    @Override
    public void onModulesLoaded(ServerContext context) {
        try {
            VertxContext vertxContext = context.cast(VertxContext.class);
            UploadContext.withVertx(vertxContext.vertx());
            vertxContext.router().post("/fileupload").handler(UploadContext.getUploadHandlerFactory().make(vertxContext.vertx()));


            vertxContext.vertx().eventBus().consumer("valid-transactions", event -> {
                validTransactions.getTransactions().add(Json.decodeValue(event.body().toString(), Transaction.class));
                if (validTransactions.getTransactions().size() == 1000) {
                    vertxContext.vertx().eventBus().publish("valid-transactions-ui", Json.encode(validTransactions));
                    validTransactions = new BulkValidTransactions();
                }
            });
            vertxContext.vertx().eventBus().consumer("invalid-transactions", event -> {
                inValidTransactions.getTransactions().add(Json.decodeValue(event.body().toString(), InvalidTransaction.class));
                if (inValidTransactions.getTransactions().size() == 1000) {
                    vertxContext.vertx().eventBus().publish("invalid-transactions-ui", Json.encode(inValidTransactions));
                    inValidTransactions = new BulkInValidTransactions();
                }
            });
        } catch (ServerContext.InvalidContextTypeException e) {
            LOGGER.error("Not a vertx context", e);
        }
    }
}
