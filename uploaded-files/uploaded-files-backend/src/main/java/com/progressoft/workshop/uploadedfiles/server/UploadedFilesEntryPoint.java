package com.progressoft.workshop.uploadedfiles.server;

import com.google.auto.service.AutoService;
import com.progressoft.brix.domino.api.server.entrypoint.ServerAppEntryPoint;
import com.progressoft.brix.domino.api.server.entrypoint.ServerContext;
import com.progressoft.brix.domino.api.server.entrypoint.VertxContext;

@AutoService(ServerAppEntryPoint.class)
public class UploadedFilesEntryPoint implements ServerAppEntryPoint {
    @Override
    public void onModulesLoaded(ServerContext context) {
        VertxContext vertxContext = (VertxContext) context;
        UploadedFilesContext.withVertx(vertxContext.vertx());
    }
}
