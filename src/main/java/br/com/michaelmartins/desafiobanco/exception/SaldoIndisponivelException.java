package br.com.michaelmartins.desafiobanco.exception;

public class SaldoIndisponivelException extends RuntimeException {

    public static final String SALDO_INSUFICIENTE = "Saldo insuficiente para a operação.";

    public SaldoIndisponivelException(String message) {
        super(message);
    }
}
