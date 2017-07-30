package com.progressoft.workshop.fileparser.server;

import com.google.auto.service.AutoService;
import com.progressoft.brix.domino.api.server.entrypoint.ServerAppEntryPoint;
import com.progressoft.brix.domino.api.server.entrypoint.ServerContext;
import com.progressoft.brix.domino.api.server.entrypoint.VertxContext;
import com.progressoft.workshop.fileparser.shared.FileStatistics;
import com.progressoft.workshop.fileparser.shared.InvalidTransaction;
import com.progressoft.workshop.fileparser.shared.Transaction;
import com.progressoft.workshop.upload.shared.FileRecord;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;

import static com.progressoft.workshop.fileparser.server.FileParserContext.withVertx;

@AutoService(ServerAppEntryPoint.class)
public class FileParserEntryPoint implements ServerAppEntryPoint {

    private VertxContext vertxContext;

    @Override
    public void onModulesLoaded(ServerContext context) {
        vertxContext = (VertxContext) context;
        withVertx(vertxContext.vertx());
        vertxContext.vertx().eventBus().consumer("file-address", this::parseFile);
    }

    private void parseFile(Message<Object> message) {
        FileParserContext.getBuilderFactory().make(mapToFileRecord(message))
                .onNextTransaction(this::onTransactionReceived)
                .onParsingError(this::onInvalidTransactionReceived)
                .onStatisticsUpdated(this::publishStatistics)
                .onParsingCompleted(this::publishCompletedStatistics)
                .build()
                .parse();
    }

    private void publishCompletedStatistics(FileStatistics statistics) {
        vertxContext.vertx().eventBus().publish("completed-stats", Json.encode(statistics));
    }

    private void publishStatistics(FileStatistics statistics) {
        vertxContext.vertx().eventBus().publish("file-stats", Json.encode(statistics));
    }

    private FileRecord mapToFileRecord(Message<Object> message) {
        return Json.decodeValue(message.body().toString(), FileRecord.class);
    }

    private void onInvalidTransactionReceived(InvalidTransaction invalidTransaction) {
        vertxContext.vertx().eventBus().publish("invalid-transactions", Json.encode(invalidTransaction));
    }

    private void onTransactionReceived(Transaction transaction) {
        vertxContext.vertx().eventBus().publish("valid-transactions", Json.encode(transaction));
    }
}
