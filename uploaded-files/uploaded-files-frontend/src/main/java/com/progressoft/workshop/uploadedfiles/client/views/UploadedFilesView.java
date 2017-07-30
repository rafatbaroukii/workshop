package com.progressoft.workshop.uploadedfiles.client.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.progressoft.brix.domino.api.client.mvp.view.View;
import com.progressoft.workshop.fileparser.shared.FileStatistics;
import com.progressoft.workshop.layout.shared.extension.LayoutContext.MenuItem;

import static com.progressoft.workshop.layout.shared.extension.LayoutContext.Content;

public interface UploadedFilesView extends View<IsWidget> {

    UploadedFilesMenuItem menuItem();

    UploadedFilesContent content();

    @FunctionalInterface
    interface MenuSelectionHandler {
        void onSelected();
    }

    interface UploadedFilesMenuItem extends MenuItem {
        void setSelectionHandler(MenuSelectionHandler handler);

        void select();
    }

    interface UploadedFilesContent extends Content {
        void add(FileStatistics fileStatistics);

        void clear();
    }
}