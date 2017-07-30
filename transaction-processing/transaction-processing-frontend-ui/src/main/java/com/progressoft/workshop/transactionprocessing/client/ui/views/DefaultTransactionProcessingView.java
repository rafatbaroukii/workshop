package com.progressoft.workshop.transactionprocessing.client.ui.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;

import com.progressoft.brix.domino.api.client.annotations.UiView;

import com.progressoft.workshop.transactionprocessing.client.presenters.TransactionProcessingPresenter;
import com.progressoft.workshop.transactionprocessing.client.views.TransactionProcessingView;

@UiView(presentable = TransactionProcessingPresenter.class)
public class DefaultTransactionProcessingView extends Composite implements TransactionProcessingView{

    interface DefaultTransactionProcessingViewUiBinder extends UiBinder<HTMLPanel, DefaultTransactionProcessingView> {
    }

    private static DefaultTransactionProcessingViewUiBinder ourUiBinder = GWT.create(DefaultTransactionProcessingViewUiBinder.class);

    @UiField
    DivElement mainDiv;

    public DefaultTransactionProcessingView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public IsWidget get() {
        return this;
    }
}