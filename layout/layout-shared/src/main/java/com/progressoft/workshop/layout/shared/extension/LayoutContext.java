package com.progressoft.workshop.layout.shared.extension;


import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.IsWidget;
import com.progressoft.brix.domino.api.shared.extension.Context;

public interface LayoutContext extends Context {

    void addMenuItem(MenuItem menuItem);

    void setContent(Content content);

    void setHeaderText(String headerText);

    interface MenuItem {
        IsWidget get();
    }

    interface Content {
        IsWidget get();
    }
}
