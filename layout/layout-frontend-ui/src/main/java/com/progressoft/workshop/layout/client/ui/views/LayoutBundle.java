package com.progressoft.workshop.layout.client.ui.views;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ExternalTextResource;
import com.google.gwt.resources.client.ImageResource;

public interface LayoutBundle extends ClientBundle{

    interface Style extends CssResource {
        String Layout();
    }

    @Source("css/Layout.gss")
    Style style();

    @Source("text/welcome.txt")
    ExternalTextResource welcome();

    @Source("images/ps-logo-transparent.jpg")
    ImageResource psLogo();

    @Source("images/background.png")
    ImageResource background();

}