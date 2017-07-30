package com.progressoft.workshop.transactionprocessing.server;

public class AccountSpy extends Account {
    public AccountSpy(String accountNumber) {
        super(accountNumber);
    }

    public double getBalance() {
        return this.balance;
    }
}
