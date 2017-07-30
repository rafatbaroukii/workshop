package com.progressoft.workshop.upload.client.ui.views;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface UploadBundle extends ClientBundle{

    interface Style extends CssResource {
        String upload();
        String card();
        String statsContentTitle();
        String uploader();
        String cardContent();
        String percentageContent();
        String cardAction();
        String cardActionContent();
        String cardActionLabel();
        String cardTotalLabel();
        String statsBody();
        String uploaderContainer();
        String statsContainer();
        String progress();
    }

    @Source("css/upload.gss")
    Style style();
}