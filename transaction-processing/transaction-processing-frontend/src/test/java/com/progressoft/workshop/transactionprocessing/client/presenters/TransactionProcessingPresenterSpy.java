package com.progressoft.workshop.transactionprocessing.client.presenters;

import com.progressoft.workshop.transactionprocessing.client.presenters.DefaultTransactionProcessingPresenter;
import com.progressoft.brix.domino.api.shared.extension.MainContext;

public class TransactionProcessingPresenterSpy extends DefaultTransactionProcessingPresenter{

    private MainContext mainContext;

    @Override
    public void contributeToMainModule(MainContext context) {
        super.contributeToMainModule(context);
        this.mainContext=context;
    }

    public MainContext getMainContext() {
        return mainContext;
    }

    @Override
    protected String getConcrete() {
        return DefaultTransactionProcessingPresenter.class.getCanonicalName();
    }
}
