package com.progressoft.workshop.upload.client.ui.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.progressoft.workshop.upload.client.views.UploadView;
import gwt.material.design.client.ui.MaterialLink;

public class DefaultUploadMenuItem extends Composite implements UploadView.UploadMenuItem {

    private UploadView.MenuSelectionHandler handler;

    interface DefaultUploadMenuItemUiBinder extends UiBinder<MaterialLink, DefaultUploadMenuItem> {
    }

    private static DefaultUploadMenuItemUiBinder ourUiBinder = GWT.create(DefaultUploadMenuItemUiBinder.class);

    MaterialLink root;

    public DefaultUploadMenuItem() {
        root=ourUiBinder.createAndBindUi(this);
        initWidget(root);
    }

    @Override
    public IsWidget get() {
        return this;
    }

    @UiHandler("uploadLink")
    void onClick(ClickEvent clickEvent) {
        handler.onSelected();
    }

    @Override
    public void selectionHandler(UploadView.MenuSelectionHandler handler) {
        this.handler = handler;
    }

    @Override
    public void select() {
        handler.onSelected();
    }
}