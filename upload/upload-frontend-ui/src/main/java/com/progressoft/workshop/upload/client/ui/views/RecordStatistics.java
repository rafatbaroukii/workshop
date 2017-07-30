package com.progressoft.workshop.upload.client.ui.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.progressoft.workshop.upload.client.views.UploadView;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialProgress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecordStatistics extends Composite {

    private static final Logger logger = LoggerFactory.getLogger(RecordStatistics.class);

    interface RecordStatisticsUiBinder extends UiBinder<MaterialCard, RecordStatistics> {

    }

    private static RecordStatisticsUiBinder ourUiBinder = GWT.create(RecordStatisticsUiBinder.class);

    interface CardStats{
        String getTotal();
        String getFirstSum();
        String getSecondSum();
        double getFirstProgress();
        double getSecondProgress();
    }

    @UiField
    MaterialLabel totalTitle;

    @UiField
    MaterialLabel firstProgressTitle;

    @UiField
    MaterialLabel secondProgressTitle;

    @UiField
    MaterialLabel totalValue;

    @UiField
    MaterialCardTitle firstProgressValue;

    @UiField
    MaterialProgress firstProgress;

    @UiField
    MaterialCardTitle secondProgressValue;

    @UiField
    MaterialProgress secondProgress;

    public RecordStatistics(String totalTitle, String firstProgressTitle, String secondProgressTitle) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.totalTitle.setText(totalTitle);
        this.firstProgressTitle.setText(firstProgressTitle);
        this.secondProgressTitle.setText(secondProgressTitle);
    }

    public void updateFirst(CardStats stats) {
        totalValue.setText(stats.getTotal());
        firstProgressValue.setText(stats.getFirstSum());
        secondProgressValue.setText(stats.getSecondSum());
        firstProgress.setPercent(stats.getFirstProgress());
        secondProgress.setPercent(stats.getSecondProgress());
    }

}