package cl.ucn.bank.application;

import cl.ucn.bank.domain.model.Account;
import cl.ucn.bank.domain.port.AccountRepository;

import java.math.BigDecimal;

/**
 * Servicio de aplicación.
 *
 * Coordina el caso de uso de transferencia. No contiene detalles de web ni de base de datos.
 */
public class TransferService {

    private final AccountRepository accountRepository;

    public TransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void transfer(Long sourceAccountId, Long targetAccountId, BigDecimal amount) {
        if (sourceAccountId.equals(targetAccountId)) {
            throw new IllegalArgumentException("Las cuentas deben ser distintas");
        }

        Account source = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta de origen no encontrada"));

        Account target = accountRepository.findById(targetAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta de destino no encontrada"));

        source.withdraw(amount);
        target.deposit(amount);

        accountRepository.save(source);
        accountRepository.save(target);
    }
}
