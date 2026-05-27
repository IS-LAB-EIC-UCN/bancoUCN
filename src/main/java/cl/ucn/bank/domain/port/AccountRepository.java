package cl.ucn.bank.domain.port;

import cl.ucn.bank.domain.model.Account;

import java.util.Optional;

/**
 * Puerto de salida.
 *
 * La aplicación necesita guardar y buscar cuentas, pero no debe saber si se usa
 * JPA, PostgreSQL, memoria, archivos o un servicio externo.
 */
public interface AccountRepository {

    Optional<Account> findById(Long id);

    void save(Account account);
}
