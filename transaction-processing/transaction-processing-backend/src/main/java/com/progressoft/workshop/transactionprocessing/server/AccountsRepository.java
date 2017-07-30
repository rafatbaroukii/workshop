package com.progressoft.workshop.transactionprocessing.server;

public interface AccountsRepository {
    Account find(String accountNumber);

    void save(Account account);

    class AccountNotFoundException extends RuntimeException {
    }
}
