package com.progressoft.workshop.upload.shared;

import com.progressoft.workshop.fileparser.shared.InvalidTransaction;

import java.util.ArrayList;
import java.util.List;

public class BulkInValidTransactions {
    private List<InvalidTransaction> transactions = new ArrayList<>();

    public List<InvalidTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<InvalidTransaction> transactions) {
        this.transactions = transactions;
    }
}
