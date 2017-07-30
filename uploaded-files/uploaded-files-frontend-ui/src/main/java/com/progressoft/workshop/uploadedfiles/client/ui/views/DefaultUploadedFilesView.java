package com.progressoft.workshop.uploadedfiles.client.ui.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;

import com.progressoft.brix.domino.api.client.annotations.UiView;

import com.progressoft.workshop.layout.shared.extension.LayoutContext;
import com.progressoft.workshop.uploadedfiles.client.presenters.UploadedFilesPresenter;
import com.progressoft.workshop.uploadedfiles.client.views.UploadedFilesView;

@UiView(presentable = UploadedFilesPresenter.class)
public class DefaultUploadedFilesView implements UploadedFilesView {

    private final UploadedFilesMenuItem menuItem = new DefaultUploadedFilesMenuItem();
    private final UploadedFilesContent content = new DefaultUploadedFilesContent();

    @Override
    public UploadedFilesMenuItem menuItem() {
        return menuItem;
    }

    @Override
    public UploadedFilesContent content() {
        return content;
    }

    @Override
    public IsWidget get() {
        return content.get();
    }
}