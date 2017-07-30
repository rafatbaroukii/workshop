package com.progressoft.workshop.transactionprocessing.server;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

    public static final String ACCOUNT_NUMBER = "11111";
    private AccountSpy account;

    @Before
    public void setUp() throws Exception {
        account = new AccountSpy(ACCOUNT_NUMBER);
    }

    @Test(expected = Account.InvalidAmountException.class)
    public void givenNewAccount_whenCreditWithMinusAmount_thenShouldThrowException() throws Exception {
        account.credit(-20.3);
    }

    @Test
    public void givenNewAccount_whenCreditWithValidAmount_thenTheBalanceShouldBeIncreasedWithTheAmount() throws Exception {
        account.credit(20.3);
        assertThat(account.getBalance()).isEqualTo(20.3);
    }

    @Test(expected = Account.InsufficientBalanceException.class)
    public void givenNewAccount_whenDebit_thenShouldThrowException() throws Exception {
        account.debit(20.3);
    }

    @Test
    public void givenNewAccountWithSufficientBalance_whenDebit_thenShouldDecreaseBalanceWithTheDebitAmount() throws Exception {
        account.credit(50);
        account.debit(25);
        assertThat(account.getBalance()).isEqualTo(25);
    }

    @Test
    public void givenAccount_whenAccept_thenShouldGetTheAccountInformation() throws Exception {
        final boolean[] isAccepted = {false};
        account.credit(50);
        account.accept((accountNumber, balance) -> {
            assertThat(accountNumber).isEqualTo(ACCOUNT_NUMBER);
            assertThat(balance).isEqualTo(50);
            isAccepted[0] = true;
        });
        assertThat(isAccepted[0]).isTrue();
    }
}
