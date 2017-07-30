package com.progressoft.workshop.uploadedfiles.client.presenters;

import com.progressoft.workshop.uploadedfiles.client.presenters.DefaultUploadedFilesPresenter;
import com.progressoft.brix.domino.api.shared.extension.MainContext;

public class UploadedFilesPresenterSpy extends DefaultUploadedFilesPresenter{

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
        return DefaultUploadedFilesPresenter.class.getCanonicalName();
    }
}
