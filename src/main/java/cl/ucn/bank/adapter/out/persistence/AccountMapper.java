package cl.ucn.bank.adapter.out.persistence;

import cl.ucn.bank.domain.model.Account;

/**
 * Traduce entre el modelo de persistencia y el modelo de dominio.
 */
public class AccountMapper {

    public Account toDomain(AccountEntity entity) {
        return new Account(entity.getId(), entity.getBalance());
    }

    public AccountEntity toEntity(Account account) {
        return new AccountEntity(account.getId(), account.getBalance());
    }
}
