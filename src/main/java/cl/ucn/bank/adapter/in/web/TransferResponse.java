package cl.ucn.bank.adapter.in.web;

/**
 * DTO de salida para simplificar la demo sin framework web.
 */
public record TransferResponse(boolean success, String message) {
}
