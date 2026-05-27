package cl.ucn.bank.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    void shouldDepositMoney() {
        Account account = new Account(1L, new BigDecimal("1000"));

        account.deposit(new BigDecimal("500"));

        assertEquals(new BigDecimal("1500"), account.getBalance());
    }

    @Test
    void shouldWithdrawMoneyWhenBalanceIsEnough() {
        Account account = new Account(1L, new BigDecimal("1000"));

        account.withdraw(new BigDecimal("400"));

        assertEquals(new BigDecimal("600"), account.getBalance());
    }

    @Test
    void shouldRejectWithdrawWhenBalanceIsInsufficient() {
        Account account = new Account(1L, new BigDecimal("1000"));

        assertThrows(IllegalStateException.class,
                () -> account.withdraw(new BigDecimal("2000")));
    }
}
