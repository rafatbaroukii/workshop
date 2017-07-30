package com.progressoft.workshop.upload.client.ui.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.progressoft.workshop.fileparser.shared.FileStatistics;
import com.progressoft.workshop.upload.client.views.UploadView;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialTitle;

public class FileStatsView extends Composite implements UploadView.UploadContent {

    interface FileStatsViewUiBinder extends UiBinder<MaterialRow, FileStatsView> {

    }

    private static FileStatsViewUiBinder ourUiBinder = GWT.create(FileStatsViewUiBinder.class);

    @FunctionalInterface
    public interface ValueFormatter {

        String format(double value);
    }

    private ValueFormatter moneyFormatter = value -> NumberFormat.getFormat("#.00").format(Math.abs(value));

    private ValueFormatter moneyTotalFormatter =
            value -> NumberFormat.getFormat("#.00").format(Math.abs(value)) + " $" + (value >= 0 ? " Credit" : " Debit");
    private ValueFormatter countFormatter = value -> NumberFormat.getFormat("#").format(value);

    private final MaterialRow root;

    @UiField
    MaterialColumn totalRecords;

    @UiField
    MaterialColumn totalAmount;

    @UiField
    MaterialColumn totalTransactions;

    @UiField
    MaterialTitle statsContentTitle;

    private RecordStatistics totalRecordsStats;

    private RecordStatistics totalAmountsStats;
    private RecordStatistics totalTransactionsStats;

    public FileStatsView() {
        root = ourUiBinder.createAndBindUi(this);
        initWidget(root);
        this.totalRecordsStats = new RecordStatistics("TOTAL RECORDS ", "VALID", "INVALID");
        this.totalAmountsStats = new RecordStatistics("TOTAL AMOUNTS", "CREDIT", "DEBIT");
        this.totalTransactionsStats = new RecordStatistics("TOTAL TRANSACTIONS", "CREDIT", "DEBIT");

        this.totalRecords.add(totalRecordsStats);
        this.totalAmount.add(totalAmountsStats);
        this.totalTransactions.add(totalTransactionsStats);

    }


    @Override
    public IsWidget get() {
        return this;
    }

    @Override
    public void update(FileStatistics stats) {
        statsContentTitle.setTitle(stats.fileName);
        this.totalRecordsStats.updateFirst(new CardStatsImpl(
                stats.totalRecords,
                stats.validTransactionsCount,
                stats.invalidTransactionsCount, countFormatter, countFormatter));

        this.totalAmountsStats.updateFirst(new CardStatsImpl(
                stats.amount,
                stats.creditAmount,
                stats.debitAmount, moneyTotalFormatter, moneyFormatter) {

            @Override
            public double getFirstProgress() {
                return 100 - Math.abs((Math.abs(stats.amount) / stats.creditAmount) * 100);
            }

            @Override
            public double getSecondProgress() {
                return 100 - Math.abs((Math.abs(stats.amount) / stats.debitAmount) * 100);
            }

        });

        this.totalTransactionsStats.updateFirst(new CardStatsImpl(
                stats.validTransactionsCount,
                stats.creditTransactionsCount,
                stats.debitTransactionsCount, countFormatter, countFormatter));
    }

    private static class CardStatsImpl implements RecordStatistics.CardStats {

        private final double total;
        private final double firstSum;
        private final double secondSum;
        private final ValueFormatter totalFormatter;
        private final ValueFormatter formatter;

        public CardStatsImpl(double total, double firstSum, double secondSum, ValueFormatter totalFormatter,
                             ValueFormatter formatter) {
            this.total = total;
            this.firstSum = firstSum;
            this.secondSum = secondSum;
            this.totalFormatter = totalFormatter;
            this.formatter = formatter;
        }

        @Override
        public String getTotal() {
            return totalFormatter.format(total);
        }

        @Override
        public String getFirstSum() {
            return formatter.format(firstSum);
        }

        @Override
        public String getSecondSum() {
            return formatter.format(secondSum);
        }

        @Override
        public double getFirstProgress() {
            return Math.abs((firstSum / Math.abs(total)) * 100);
        }

        @Override
        public double getSecondProgress() {
            return Math.abs((secondSum / Math.abs(total)) * 100);
        }
    }
}