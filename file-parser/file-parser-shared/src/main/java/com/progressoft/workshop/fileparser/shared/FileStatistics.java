package com.progressoft.workshop.fileparser.shared;

import java.util.Date;

public class FileStatistics {

    public String fileName;
    public Date uploadDate;

    public double totalRecords;
    public double validTransactionsCount;
    public double invalidTransactionsCount;

    public double amount;
    public double creditAmount;
    public double debitAmount;

    public double creditTransactionsCount;
    public double debitTransactionsCount;
    private String _id;

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }
}
