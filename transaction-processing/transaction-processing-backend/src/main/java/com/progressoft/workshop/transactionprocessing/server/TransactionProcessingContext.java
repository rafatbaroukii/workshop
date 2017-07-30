package com.progressoft.workshop.transactionprocessing.server;

public class TransactionProcessingContext {
    private static TransactionHandlerFactory factory = new TransactionHandlerFactory() {
    };
    private static AccountsRepository accountsRepository = new InMemoryAccountsRepository();

    public static void withTransactionHandlerFactory(TransactionHandlerFactory factory) {
        TransactionProcessingContext.factory = factory;
    }

    public static TransactionHandlerFactory transactionHandlerFactory() {
        return factory;
    }

    public static AccountsRepository accountsRepository() {
        return accountsRepository;
    }
}
