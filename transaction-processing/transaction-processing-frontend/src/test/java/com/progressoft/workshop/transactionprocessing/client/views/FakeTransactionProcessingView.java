package com.progressoft.workshop.transactionprocessing.client.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.progressoft.workshop.transactionprocessing.client.views.TransactionProcessingView;
import com.progressoft.workshop.transactionprocessing.client.presenters.TransactionProcessingPresenter;
import com.progressoft.brix.domino.api.client.annotations.UiView;

@UiView(presentable=TransactionProcessingPresenter.class)
public class FakeTransactionProcessingView implements TransactionProcessingView {

    @Override
    public IsWidget get() {
        return null;
    }
}
