package cl.ucn.bank.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * Entidad de persistencia.
 *
 * Esta clase sí usa JPA, pero queda fuera del dominio. Por eso pertenece al adaptador de salida.
 */
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    private Long id;

    private BigDecimal balance;

    protected AccountEntity() {
        // Constructor requerido por JPA.
    }

    public AccountEntity(Long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
