package cl.ucn.bank.adapter.out.persistence;

import cl.ucn.bank.domain.model.Account;
import cl.ucn.bank.domain.port.AccountRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Adaptador de salida en memoria.
 *
 * Sirve para ejecutar el proyecto sin levantar una base de datos real.
 */
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<Long, AccountEntity> accounts = new HashMap<>();
    private final AccountMapper mapper = new AccountMapper();

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(accounts.get(id)).map(mapper::toDomain);
    }

    @Override
    public void save(Account account) {
        accounts.put(account.getId(), mapper.toEntity(account));
    }
}
