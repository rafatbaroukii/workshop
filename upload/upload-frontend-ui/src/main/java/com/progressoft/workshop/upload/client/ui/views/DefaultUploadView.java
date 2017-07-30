package com.progressoft.workshop.upload.client.ui.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.progressoft.brix.domino.api.client.annotations.UiView;
import com.progressoft.workshop.upload.client.presenters.UploadPresenter;
import com.progressoft.workshop.upload.client.views.UploadView;

@UiView(presentable = UploadPresenter.class)
public class DefaultUploadView implements UploadView {

    private UploadMenuItem menuItem = new DefaultUploadMenuItem();
    private UploadContent content = new DefaultUploadContent();

    @Override
    public UploadMenuItem menuItem() {
        return menuItem;
    }

    @Override
    public UploadContent content() {
        return content;
    }

    @Override
    public IsWidget get() {
        return content.get();
    }
}
