package com.progressoft.workshop.upload.client.presenters;

import com.progressoft.brix.domino.api.shared.extension.MainContext;
import com.progressoft.brix.domino.vertxbus.shared.extension.VertxBusContext;
import com.progressoft.workshop.layout.shared.extension.LayoutContext;

public class UploadPresenterSpy extends DefaultUploadPresenter {

    private MainContext mainContext;
    private LayoutContext layoutContext;
    private VertxBusContext vertxBusContext;

    @Override
    public void contributeToMainModule(MainContext context) {
        super.contributeToMainModule(context);
        this.mainContext = context;
    }

    public MainContext getMainContext() {
        return mainContext;
    }

    @Override
    protected String getConcrete() {
        return DefaultUploadPresenter.class.getCanonicalName();
    }

    @Override
    public void contributeToLayoutModule(LayoutContext layoutContext) {
        super.contributeToLayoutModule(layoutContext);
        this.layoutContext = layoutContext;
    }

    @Override
    public void contributeToEventBusModule(VertxBusContext context) {
        super.contributeToEventBusModule(context);
        this.vertxBusContext = context;
    }

    public LayoutContext getLayoutContext() {
        return layoutContext;
    }

    public VertxBusContext getBusContext() {
        return this.vertxBusContext;
    }
}
