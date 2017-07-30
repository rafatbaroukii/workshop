package com.progressoft.workshop.uploadedfiles.client.ui.views;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ExternalTextResource;

public interface UploadedFilesBundle extends ClientBundle{

    interface Style extends CssResource {
        String uploadedFiles();
        String collectionFileName();
        String collectionFileDate();
        String entry();

        String left();
        String right();

        String progress();
        String progressGreen();
        String progressRed();

        String progressSmall();
        String progressSmallGreen();
        String progressSmallRed();

        String creditLabel();
        String debitLabel();

        String progressLabel();
    }

    @Source("css/UploadedFiles.gss")
    Style style();

    @Source("text/welcome.txt")
    ExternalTextResource welcome();

}