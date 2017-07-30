package com.progressoft.workshop.fileparser.shared;

public class Transaction {
    private String accountNumber;
    private String operation;
    private double amount;
    private String fileName;

    public Transaction() {
    }

    public Transaction(String accountNumber, String operation, double amount, String fileName) {
        this.accountNumber = accountNumber;
        this.operation = operation;
        this.amount = amount;
        this.fileName = fileName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (Double.compare(that.amount, amount) != 0) return false;
        if (!accountNumber.equals(that.accountNumber)) return false;
        return operation.equals(that.operation);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = accountNumber.hashCode();
        result = 31 * result + operation.hashCode();
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "accountNumber='" + accountNumber + '\'' +
                ", operation='" + operation + '\'' +
                ", amount=" + amount +
                '}';
    }
}
