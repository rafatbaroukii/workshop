package com.progressoft.workshop.upload.client.views;

import com.google.gwt.user.client.ui.IsWidget;
import com.progressoft.brix.domino.api.client.annotations.UiView;
import com.progressoft.workshop.fileparser.shared.FileStatistics;
import com.progressoft.workshop.upload.client.presenters.UploadPresenter;

@UiView(presentable = UploadPresenter.class)
public class FakeUploadView implements UploadView {

    private boolean menuSelected;
    private FileStatistics stats;

    private final UploadContent content = new UploadContent() {
        @Override
        public void update(FileStatistics stats) {
            FakeUploadView.this.stats = stats;
        }

        @Override
        public IsWidget get() {
            return null;
        }
    };
    private MenuSelectionHandler handler;
    private UploadMenuItem uploadMenuItem = new UploadMenuItem() {
        @Override
        public void selectionHandler(MenuSelectionHandler handler) {
            FakeUploadView.this.handler = handler;
        }

        @Override
        public void select() {
            handler.onSelected();
            menuSelected = true;
        }

        @Override
        public IsWidget get() {
            return null;
        }
    };

    @Override
    public IsWidget get() {
        return null;
    }

    @Override
    public UploadMenuItem menuItem() {
        return uploadMenuItem;
    }

    @Override
    public UploadContent content() {
        return content;
    }

    public void selectMenuItem() {
        this.handler.onSelected();
    }

    public boolean isMenuSelected() {
        return menuSelected;
    }

    public FileStatistics getStats() {
        return stats;
    }
}
