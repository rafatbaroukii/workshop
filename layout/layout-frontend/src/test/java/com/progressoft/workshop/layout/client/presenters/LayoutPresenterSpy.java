package com.progressoft.workshop.layout.client.presenters;

import com.progressoft.workshop.layout.client.presenters.DefaultLayoutPresenter;
import com.progressoft.brix.domino.api.shared.extension.MainContext;

public class LayoutPresenterSpy extends DefaultLayoutPresenter{

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
        return DefaultLayoutPresenter.class.getCanonicalName();
    }
}
