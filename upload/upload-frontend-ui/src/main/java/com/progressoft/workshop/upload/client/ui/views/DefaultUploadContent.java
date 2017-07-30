package com.progressoft.workshop.upload.client.ui.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.progressoft.workshop.fileparser.shared.FileStatistics;
import com.progressoft.workshop.upload.client.views.UploadView;
import gwt.material.design.addins.client.fileuploader.MaterialUploadLabel;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialPanel;

import java.util.HashMap;
import java.util.Map;

public class DefaultUploadContent extends Composite implements UploadView.UploadContent {


    private final MaterialPanel root;

    interface DefaultUploadContentUiBinder extends UiBinder<MaterialPanel, DefaultUploadContent> {
    }

    private static DefaultUploadContentUiBinder ourUiBinder = GWT.create(DefaultUploadContentUiBinder.class);

    private static final Map<String, FileStatsView> FILE_STATS_VIEWS = new HashMap<>();

    @UiField
    MaterialUploadLabel uploaderLabel;

    public DefaultUploadContent() {
        root = ourUiBinder.createAndBindUi(this);
        initWidget(root);
        this.uploaderLabel.getIcon().setIconColor(Color.LIGHT_BLUE_ACCENT_4);
    }

    @Override
    public void update(FileStatistics stats) {
        if (!FILE_STATS_VIEWS.containsKey(stats.fileName)) {
            FileStatsView fileStatsView = new FileStatsView();
            root.add(fileStatsView);
            FILE_STATS_VIEWS.put(stats.fileName, fileStatsView);
        }
        FILE_STATS_VIEWS.get(stats.fileName).update(stats);
    }

    @UiHandler("clearButton")
    void onClear(ClickEvent event) {
        FILE_STATS_VIEWS.values().forEach(Widget::removeFromParent);
        FILE_STATS_VIEWS.clear();
    }

    @Override
    public IsWidget get() {
        return this;
    }
}