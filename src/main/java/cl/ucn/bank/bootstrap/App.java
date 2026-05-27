package cl.ucn.bank.bootstrap;

import cl.ucn.bank.adapter.in.web.AccountController;
import cl.ucn.bank.adapter.in.web.TransferRequest;
import cl.ucn.bank.adapter.in.web.TransferResponse;
import cl.ucn.bank.adapter.out.persistence.InMemoryAccountRepository;
import cl.ucn.bank.application.TransferService;
import cl.ucn.bank.domain.model.Account;

import java.math.BigDecimal;

/**
 * Punto de arranque manual.
 *
 * Conecta adaptadores con la aplicación sin usar Spring.
 */
public class App {

    public static void main(String[] args) {
        InMemoryAccountRepository repository = new InMemoryAccountRepository();
        repository.save(new Account(1L, new BigDecimal("100000")));
        repository.save(new Account(2L, new BigDecimal("50000")));

        TransferService transferService = new TransferService(repository);
        AccountController controller = new AccountController(transferService);

        TransferResponse response = controller.transfer(
                new TransferRequest(1L, 2L, new BigDecimal("25000"))
        );

        System.out.println(response.message());
        System.out.println("Saldo cuenta 1: " + repository.findById(1L).orElseThrow().getBalance());
        System.out.println("Saldo cuenta 2: " + repository.findById(2L).orElseThrow().getBalance());
    }
}
