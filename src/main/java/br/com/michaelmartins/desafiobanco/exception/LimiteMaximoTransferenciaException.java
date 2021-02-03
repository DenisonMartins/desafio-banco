package br.com.michaelmartins.desafiobanco.exception;

public class LimiteMaximoTransferenciaException extends RuntimeException {

    public static final String MESSAGE_VALOR_LIMITE_TRANSFERENCIA = "Operação de transferência tem um limite máximo de 500 por operação.";

    public LimiteMaximoTransferenciaException(String message) {
        super(message);
    }
}
