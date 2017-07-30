package com.progressoft.workshop.transactionprocessing.client.ui.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ExternalTextResource;
import com.google.gwt.resources.client.ImageResource;

public interface TransactionProcessingBundle extends ClientBundle{

    interface Style extends CssResource {
        String TransactionProcessing();
    }

    @Source("css/TransactionProcessing.gss")
    Style style();

    @Source("text/welcome.txt")
    ExternalTextResource welcome();

}