package com.progressoft.workshop.upload.client.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.progressoft.brix.domino.api.client.mvp.view.View;
import com.progressoft.workshop.fileparser.shared.FileStatistics;
import com.progressoft.workshop.layout.shared.extension.LayoutContext;

public interface UploadView extends View<IsWidget> {
    UploadMenuItem menuItem();

    UploadContent content();

    @FunctionalInterface
    interface MenuSelectionHandler {
        void onSelected();
    }

    interface UploadMenuItem extends LayoutContext.MenuItem {
        void selectionHandler(MenuSelectionHandler handler);

        void select();
    }

    interface UploadContent extends LayoutContext.Content {
        void update(FileStatistics stats);
    }
}