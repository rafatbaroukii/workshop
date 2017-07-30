package com.progressoft.workshop.transactionprocessing.server;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAccountsRepository implements AccountsRepository {

    private Map<String, Account> accounts = new HashMap<>();

    {
        accounts.put("12345", new Account("12345"));
        accounts.put("12346", new Account("12346"));
        accounts.put("12347", new Account("12347"));
        accounts.put("12348", new Account("12348"));
        accounts.put("12349", new Account("12349"));
        accounts.put("51234", new Account("51234"));
        accounts.put("61234", new Account("61234"));
        accounts.put("71234", new Account("71234"));
        accounts.put("81234", new Account("81234"));
        accounts.put("91234", new Account("91234"));
    }

    @Override
    public Account find(String accountNumber) {
        if (accounts.containsKey(accountNumber))
            return accounts.get(accountNumber);
        throw new AccountNotFoundException();
    }

    @Override
    public void save(Account account) {
        accounts.put(account.getAccountNumber(), account);
    }
}
