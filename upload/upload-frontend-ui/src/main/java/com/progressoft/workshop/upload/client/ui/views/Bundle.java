package com.progressoft.workshop.upload.client.ui.views;

import com.google.gwt.core.client.GWT;

public class Bundle {

    public static final UploadBundle INSTANCE= GWT.create(UploadBundle.class);

    private Bundle() {
    }
}
