package com.progressoft.workshop.upload.server;

import com.progressoft.workshop.upload.server.endpoint.DefaultUploadHandler;
import io.vertx.core.Vertx;
import io.vertx.core.file.FileSystem;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.FileUploadImpl;

public class DefaultUploadHandlerSpy extends DefaultUploadHandler {
    private String uploadName;
    private String fileName;
    private String content;
    private boolean fail = false;

    public DefaultUploadHandlerSpy(Vertx vertx) {
        super(vertx);
    }

    @Override
    protected void onCompleted(String content, String uploadName, String fileName) {
        super.onCompleted(content, uploadName, fileName);
        this.uploadName = uploadName;
        this.fileName = fileName;
        this.content = content;
    }

    @Override
    protected FileSystem readUploadedFile(RoutingContext routingContext, FileUpload fileUpload) {
        if (fail)
            return super.readUploadedFile(routingContext, new FileUploadImpl("", null));
        return super.readUploadedFile(routingContext, fileUpload);
    }

    public String getUploadName() {
        return uploadName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUploadedContent() {
        return content;
    }

    public void fail() {
        this.fail = true;
    }
}
