package cl.ucn.bank.adapter.in.web;

import java.math.BigDecimal;

/**
 * DTO de entrada. En una aplicación real podría venir de JSON o de un formulario web.
 */
public record TransferRequest(Long sourceAccountId, Long targetAccountId, BigDecimal amount) {
}
