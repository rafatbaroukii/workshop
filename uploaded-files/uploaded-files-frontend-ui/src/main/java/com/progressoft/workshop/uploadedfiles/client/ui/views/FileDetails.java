package com.progressoft.workshop.uploadedfiles.client.ui.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.progressoft.workshop.fileparser.shared.FileStatistics;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialProgress;

public class FileDetails extends Composite {


    interface FileDetailsUiBinder extends UiBinder<MaterialCard, FileDetails> {
    }

    private static FileDetailsUiBinder ourUiBinder = GWT.create(FileDetailsUiBinder.class);

    @UiField
    MaterialLabel totalRecords;

    @UiField
    MaterialLabel invalidRecords;

    @UiField
    MaterialProgress invalidRecordsProgress;

    @UiField
    MaterialLabel validRecords;

    @UiField
    MaterialProgress validRecordsProgress;

    @UiField
    MaterialIcon cardIcon;

    @UiField
    MaterialLabel fileName;

    @UiField
    MaterialLabel uploadDate;

    @UiField
    MaterialLabel totalAmount;

    @UiField
    MaterialLabel debitAmount;

    @UiField
    MaterialLabel creditAmount;

    @UiField
    MaterialLabel totalTransactions;

    @UiField
    MaterialLabel debitTransactions;

    @UiField
    MaterialProgress debitTransactionsProgress;

    @UiField
    MaterialLabel creditTransactions;

    @UiField
    MaterialProgress creditTransactionsProgress;

    private ValueFormatter moneyFormatter = value -> NumberFormat.getFormat("#.00").format(Math.abs(value));

    private ValueFormatter moneyTotalFormatter =
            value -> NumberFormat.getFormat("#.00").format(Math.abs(value)) + " $" + (value >= 0 ? " Credit" : " Debit");
    private ValueFormatter countFormatter = value -> NumberFormat.getFormat("#").format(value);


    public FileDetails(FileStatistics statistics) {
        initWidget(ourUiBinder.createAndBindUi(this));

        if (statistics.amount >= 0) {
            cardIcon.setIconType(IconType.TRENDING_UP);
            cardIcon.setTextColor(Color.GREEN);
            totalAmount.setTextColor(Color.GREEN_DARKEN_4);
        } else {
            cardIcon.setIconType(IconType.TRENDING_DOWN);
            cardIcon.setTextColor(Color.RED);
            totalAmount.setTextColor(Color.RED_DARKEN_4);
        }

        fileName.setText(statistics.fileName);
        uploadDate.setText(statistics.uploadDate.toString());

        totalAmount.setText(moneyFormatter.format(statistics.amount));

        debitAmount.setText(moneyFormatter.format(statistics.debitAmount));
        creditAmount.setText(moneyFormatter.format(statistics.creditAmount));

        totalTransactions.setText(countFormatter.format(statistics.totalRecords - statistics.invalidTransactionsCount));
        creditTransactions.setText(countFormatter.format(statistics.creditTransactionsCount));
        debitTransactions.setText(countFormatter.format(statistics.debitTransactionsCount));

        totalRecords.setText(countFormatter.format(statistics.totalRecords));
        validRecords.setText(countFormatter.format(statistics.validTransactionsCount));
        invalidRecords.setText(countFormatter.format(statistics.invalidTransactionsCount));

        invalidRecordsProgress.setPercent( Math.abs((statistics.invalidTransactionsCount / Math.abs(statistics.totalRecords)) * 100));
        validRecordsProgress.setPercent( Math.abs((statistics.validTransactionsCount / Math.abs(statistics.totalRecords)) * 100));

        debitTransactionsProgress.setPercent( Math.abs((statistics.debitTransactionsCount / Math.abs(statistics.validTransactionsCount)) * 100));
        creditTransactionsProgress.setPercent( Math.abs((statistics.creditTransactionsCount / Math.abs(statistics.validTransactionsCount)) * 100));


    }

    private interface ValueFormatter {
        String format(double number);
    }
}