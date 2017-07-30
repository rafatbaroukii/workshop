package com.progressoft.workshop.upload.shared;

import com.progressoft.workshop.fileparser.shared.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BulkValidTransactions {
    private List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
