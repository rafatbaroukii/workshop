package com.progressoft.workshop.transactionprocessing.client.presenters;

import com.progressoft.brix.domino.api.client.annotations.Presenter;
import com.progressoft.brix.domino.api.client.mvp.presenter.BaseClientPresenter;
import com.progressoft.workshop.transactionprocessing.client.views.TransactionProcessingView;
import com.progressoft.brix.domino.api.shared.extension.MainContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Presenter
public class DefaultTransactionProcessingPresenter extends BaseClientPresenter<TransactionProcessingView> implements TransactionProcessingPresenter {

    private static final Logger LOGGER=LoggerFactory.getLogger(DefaultTransactionProcessingPresenter.class);

    @Override
    public void contributeToMainModule(MainContext context){
        LOGGER.info("Main context received at presenter "+DefaultTransactionProcessingPresenter.class.getSimpleName());
    }
}