package com.progressoft.workshop.uploadedfiles.client.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.progressoft.brix.domino.api.client.annotations.UiView;
import com.progressoft.workshop.uploadedfiles.client.presenters.UploadedFilesPresenter;

@UiView(presentable=UploadedFilesPresenter.class)
public class FakeUploadedFilesView implements UploadedFilesView {

    @Override
    public IsWidget get() {
        return null;
    }

    @Override
    public UploadedFilesMenuItem menuItem() {
        return null;
    }

    @Override
    public UploadedFilesContent content() {
        return null;
    }
}
