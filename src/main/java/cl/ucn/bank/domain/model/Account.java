package cl.ucn.bank.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Entidad de dominio pura.
 *
 * No tiene anotaciones de Spring ni JPA. La regla de negocio vive aquí:
 * una cuenta no puede quedar con saldo negativo.
 */
public class Account {

    private final Long id;
    private BigDecimal balance;

    public Account(Long id, BigDecimal initialBalance) {
        if (id == null) {
            throw new IllegalArgumentException("El id de la cuenta es obligatorio");
        }
        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.id = id;
        this.balance = initialBalance;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deposit(BigDecimal amount) {
        validatePositiveAmount(amount);
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        validatePositiveAmount(amount);
        if (amount.compareTo(this.balance) > 0) {
            throw new IllegalStateException("Saldo insuficiente");
        }
        this.balance = this.balance.subtract(amount);
    }

    private void validatePositiveAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Account account)) {
            return false;
        }
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
