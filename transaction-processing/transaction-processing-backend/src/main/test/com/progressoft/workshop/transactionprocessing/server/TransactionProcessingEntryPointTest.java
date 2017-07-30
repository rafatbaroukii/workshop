package com.progressoft.workshop.transactionprocessing.server;

import com.progressoft.brix.domino.test.api.DominoTestServer;
import com.progressoft.workshop.fileparser.shared.Transaction;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.Router;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.progressoft.workshop.transactionprocessing.server.DefaultTransactionProcessingHandler.UnsupportedTransactionOperationException;
import static com.progressoft.workshop.transactionprocessing.server.TransactionProcessingContext.accountsRepository;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(VertxUnitRunner.class)
public class TransactionProcessingEntryPointTest {

    private static final String VALID_TRANSACTIONS_ADDRESS = "valid-transactions";
    private static final String CREDIT = "C";
    private static final String DEBIT = "D";
    private static final String ACCOUNT_NUMBER = "11111";
    private static final double AMOUNT = 20.0;

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

    private Vertx vertx;
    private DefaultTransactionProcessingHandlerSpy handlerSpy;

    @Before
    public void setUp(TestContext context) {
        vertx = rule.vertx();
        new DominoTestServer(vertx) {
            @Override
            public void onBeforeDominoLoad(Router router, JsonObject config) {
                TransactionProcessingContext.withTransactionHandlerFactory(new TransactionHandlerFactory() {
                    @Override
                    public Handler<Message<String>> make() {
                        handlerSpy = new DefaultTransactionProcessingHandlerSpy();
                        return handlerSpy;
                    }
                });
            }
        }.start(context);
    }

    private Account saveAccount(String accountNumber) {
        Account account = new Account(accountNumber);
        accountsRepository().save(account);
        return account;
    }

    private void publishTransaction(String accountNumber, String operation) {
        vertx.eventBus().publish(VALID_TRANSACTIONS_ADDRESS,
                Json.encode(transaction(accountNumber, operation)));
    }

    private void publishTransaction(String operation) {
        vertx.eventBus().publish(VALID_TRANSACTIONS_ADDRESS,
                Json.encode(transaction(ACCOUNT_NUMBER, operation)));
    }

    private Transaction transaction(String accountNumber, String operation) {
        return new Transaction(accountNumber, operation, AMOUNT, "fileName");
    }

    @Test
    public void givenTransactionProcessingModule_whenInitialized_thenShouldStartListeningToTransactionsEventBusAddress(TestContext context) {
        Async async = context.async();
        saveAccount(ACCOUNT_NUMBER);
        handlerSpy.onAsyncCompleted(transaction -> {
            assertThat(transaction).isNotNull();
            async.complete();
        });
        publishTransaction(CREDIT);
    }

    @Test
    public void givenTransactionProcessingModule_whenCreditTransactionIsReceivedForExistingAccount_thenShouldCreditAmountForTheAccount(TestContext context) {
        Async async = context.async();
        Account account = saveAccount(ACCOUNT_NUMBER);
        handlerSpy.onAsyncCompleted(transaction -> {
            account.accept((accountNumber, balance) -> assertThat(balance).isEqualTo(AMOUNT));
            async.complete();
        });
        publishTransaction(CREDIT);
    }

    @Test
    public void givenTransactionProcessingModule_whenDebitTransactionIsReceivedForExistingAccount_thenShouldDebitAmountForTheAccount(TestContext context) {
        Async async = context.async();
        Account account = saveAccount(ACCOUNT_NUMBER);
        account.credit(50);
        handlerSpy.onAsyncCompleted(transaction -> {
            account.accept((accountNumber, balance) ->
                    assertThat(balance).isEqualTo(50 - AMOUNT));
            async.complete();
        });
        publishTransaction(DEBIT);
    }

    @Test
    public void givenTransactionProcessingModule_whenUnsupportedTransactionIsReceived_thenShouldThrowException(TestContext context) {
        Async async = context.async();
        handlerSpy.onError(error -> {
            assertThat(error).isExactlyInstanceOf(UnsupportedTransactionOperationException.class);
            async.complete();
        });
        publishTransaction("unsupported-operation");
    }

    @Test
    public void givenTransactionProcessingModule_whenTransactionWithNonExistAccountIsReceived_thenShouldThrowException(TestContext context) throws Exception {
        Async async = context.async();
        handlerSpy.onError(error -> {
            assertThat(error).isExactlyInstanceOf(AccountsRepository.AccountNotFoundException.class);
            async.complete();
        });
        publishTransaction("non-exist-account", CREDIT);
    }
}
