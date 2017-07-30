package com.progressoft.workshop.transactionprocessing.server;

public class Account {
    private final String accountNumber;
    protected double balance = 0;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void credit(double amount) {
        if (amount < 0)
            throw new InvalidAmountException();
        this.balance += amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void debit(double amount) {
        if (this.balance < amount)
            throw new InsufficientBalanceException();
        this.balance -= amount;
    }

    public void accept(AccountVisitor visitor) {
        visitor.visit(accountNumber, balance);
    }

    @FunctionalInterface
    public interface AccountVisitor {
        void visit(String accountNumber, double balance);
    }

    public class InvalidAmountException extends RuntimeException {

    }

    public class InsufficientBalanceException extends RuntimeException {
    }
}
