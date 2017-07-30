package com.progressoft.workshop.uploadedfiles.client.presenters;

import com.progressoft.brix.domino.api.client.mvp.presenter.Presentable;
import com.progressoft.brix.domino.api.shared.extension.MainExtensionPoint;
import com.progressoft.brix.domino.api.shared.extension.MainContext;
import com.progressoft.brix.domino.api.client.annotations.InjectContext;
import com.progressoft.workshop.fileparser.shared.FilesStats;
import com.progressoft.workshop.layout.shared.extension.LayoutContext;
import com.progressoft.workshop.layout.shared.extension.LayoutExtensionPoint;

public interface UploadedFilesPresenter extends Presentable{

    @InjectContext(extensionPoint=MainExtensionPoint.class)
    void contributeToMainModule(MainContext context);

    @InjectContext(extensionPoint = LayoutExtensionPoint.class)
    void contributeToLayoutModule(LayoutContext layoutContext);

    void onFilesLoaded(FilesStats filesStats);
}