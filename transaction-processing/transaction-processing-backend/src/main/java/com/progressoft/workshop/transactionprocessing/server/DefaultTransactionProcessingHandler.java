package com.progressoft.workshop.transactionprocessing.server;

import com.progressoft.workshop.fileparser.shared.Transaction;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.progressoft.workshop.transactionprocessing.server.TransactionProcessingContext.accountsRepository;

public class DefaultTransactionProcessingHandler implements Handler<Message<String>> {

    @FunctionalInterface
    interface TransactionOperation {
        void execute(Account account, double amount);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTransactionProcessingHandler.class);

    private static final Map<String, TransactionOperation> operations = new HashMap<>();

    static {
        operations.put("D", Account::debit);
        operations.put("C", Account::credit);
    }

    @Override
    public void handle(Message<String> message) {
        processTransaction(asTransaction(message));
    }

    private Transaction asTransaction(Message<String> message) {
        return Json.decodeValue(message.body(), Transaction.class);
    }

    private void processTransaction(Transaction transaction) {
        Stream.of(accountFor(transaction))
                .forEach(account -> {
                    executeTransaction(transaction, account);
                    accountsRepository().save(account);
                });
    }

    private void executeTransaction(Transaction transaction, Account account) {
        if (!operations.containsKey(transaction.getOperation()))
            throw new UnsupportedTransactionOperationException();
        operations.get(transaction.getOperation()).execute(account, transaction.getAmount());
    }

    private Account accountFor(Transaction transaction) {
        return accountsRepository().find(transaction.getAccountNumber());
    }

    public static class UnsupportedTransactionOperationException extends RuntimeException {
    }

    public static void main(String[] args) {

        List<String> accounts = Arrays.asList("12345", "12346", "12347", "12348", "12349", "51234", "61234", "71234", "81234", "91234");
        List<String> operations = Arrays.asList("D", "C", "C", "C", "D", "I");

        IntStream.range(1, 11).forEach(fileNumber -> {
            try {
                FileWriter fileWriter = new FileWriter(new File("/home/u343/Documents/testfiles/trans_small" + fileNumber + ".csv"));
                IntStream.range(0, 100000).forEach(i -> {
                    Random rand = new Random(System.currentTimeMillis() + new Random().nextInt(1000));
                    try {
                        String line = accounts.get(rand.nextInt(10)) + "," + operations.get(rand.nextInt(6)) + "," + rand.nextInt(100) + "." + rand.nextInt(100) + "\n";
                        fileWriter.write(line);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
}
