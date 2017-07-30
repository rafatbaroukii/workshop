package com.progressoft.workshop.uploadedfiles.client.ui.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.progressoft.workshop.uploadedfiles.client.views.UploadedFilesView;
import gwt.material.design.client.ui.MaterialLink;

public class DefaultUploadedFilesMenuItem extends Composite implements UploadedFilesView.UploadedFilesMenuItem {

    private UploadedFilesView.MenuSelectionHandler handler;

    interface DefaultUploadedFilesMenuItemUiBinder extends UiBinder<MaterialLink, DefaultUploadedFilesMenuItem> {
    }

    private static DefaultUploadedFilesMenuItemUiBinder ourUiBinder = GWT.create(DefaultUploadedFilesMenuItemUiBinder.class);

    public DefaultUploadedFilesMenuItem() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public void setSelectionHandler(UploadedFilesView.MenuSelectionHandler handler) {
        this.handler = handler;
    }

    @Override
    public void select() {
        handler.onSelected();
    }

    @UiHandler("filesLink")
    public void onClick(ClickEvent event) {
        select();
    }

    @Override
    public IsWidget get() {
        return this;
    }


}
