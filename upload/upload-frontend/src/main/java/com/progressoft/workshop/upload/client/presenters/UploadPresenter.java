package com.progressoft.workshop.upload.client.presenters;

import com.progressoft.brix.domino.api.client.annotations.InjectContext;
import com.progressoft.brix.domino.api.client.mvp.presenter.Presentable;
import com.progressoft.brix.domino.api.shared.extension.MainContext;
import com.progressoft.brix.domino.api.shared.extension.MainExtensionPoint;
import com.progressoft.brix.domino.vertxbus.shared.extension.VertxBusContext;
import com.progressoft.brix.domino.vertxbus.shared.extension.VertxBusExtensionPoint;
import com.progressoft.workshop.layout.shared.extension.LayoutContext;
import com.progressoft.workshop.layout.shared.extension.LayoutExtensionPoint;

public interface UploadPresenter extends Presentable {

    String VALID_TRANSACTIONS_UI = "valid-transactions-ui";
    String INVALID_TRANSACTIONS_UI = "invalid-transactions-ui";

    @InjectContext(extensionPoint = MainExtensionPoint.class)
    void contributeToMainModule(MainContext context);

    @InjectContext(extensionPoint = LayoutExtensionPoint.class)
    void contributeToLayoutModule(LayoutContext context);

    @InjectContext(extensionPoint = VertxBusExtensionPoint.class)
    void contributeToEventBusModule(VertxBusContext context);
}