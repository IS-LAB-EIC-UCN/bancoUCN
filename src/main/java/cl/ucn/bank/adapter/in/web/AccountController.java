package cl.ucn.bank.adapter.in.web;

import cl.ucn.bank.application.TransferService;

/**
 * Adaptador de entrada.
 *
 * Simula un controlador web. Recibe una solicitud externa y llama al caso de uso.
 */
public class AccountController {

    private final TransferService transferService;

    public AccountController(TransferService transferService) {
        this.transferService = transferService;
    }

    public TransferResponse transfer(TransferRequest request) {
        transferService.transfer(
                request.sourceAccountId(),
                request.targetAccountId(),
                request.amount()
        );
        return new TransferResponse(true, "Transferencia realizada correctamente");
    }
}
