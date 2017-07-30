package com.progressoft.workshop.fileparser.server;

import com.progressoft.workshop.fileparser.shared.FileStatistics;
import com.progressoft.workshop.fileparser.shared.Transaction;
import io.reactivex.subjects.PublishSubject;

import java.util.Date;

import static com.progressoft.workshop.fileparser.server.FileParser.SupportedOperations.CREDIT;

class FileAnalyzer {

    private FileStatistics statistics = new FileStatistics();
    private PublishSubject<FileStatistics> statisticsPublishSubject = PublishSubject.create();

    @FunctionalInterface
    public interface StatisticsListener {
        void onResultsUpdated(FileStatistics statistics);
    }

    public FileAnalyzer(String fileName, Date uploadDate, StatisticsListener listener) {
        statistics.fileName = fileName;
        statistics.uploadDate = uploadDate;
        statisticsPublishSubject.window(1000)
                .doOnComplete(() -> informListener(listener))
                .subscribe(statisticsObservable -> informListener(listener));
    }

    private void informListener(StatisticsListener listener) {
        listener.onResultsUpdated(statistics);
    }

    public void update(Transaction transaction) {
        statistics.totalRecords++;
        statistics.validTransactionsCount++;
        if (CREDIT.equals(transaction.getOperation())) {
            statistics.amount += transaction.getAmount();
            statistics.creditAmount += transaction.getAmount();
            statistics.creditTransactionsCount++;
        } else {
            statistics.amount -= transaction.getAmount();
            statistics.debitAmount += transaction.getAmount();
            statistics.debitTransactionsCount++;
        }
        publishUpdates();
    }

    public void updateInvalid() {
        statistics.totalRecords++;
        statistics.invalidTransactionsCount++;
        publishUpdates();
    }

    public FileStatistics parseCompleted() {
        statisticsPublishSubject.onComplete();
        return statistics;
    }

    private void publishUpdates() {
        statisticsPublishSubject.onNext(statistics);
    }

}
