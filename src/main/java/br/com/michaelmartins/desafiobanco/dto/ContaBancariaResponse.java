package br.com.michaelmartins.desafiobanco.dto;

import br.com.michaelmartins.desafiobanco.domain.ContaBancaria;

public class ContaBancariaResponse {

    public static final String CONTA_CADASTRADA_COM_SUCESSO = "Conta cadastrada com sucesso!";

    private Long id;
    private String numeroConta;
    private String message;

    public ContaBancariaResponse(Long id, String numeroConta, String message) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.message = message;
    }

    public ContaBancariaResponse(ContaBancaria entity) {
        this.id = entity.getId();
        this.numeroConta = entity.getNumeroConta();
        this.message = CONTA_CADASTRADA_COM_SUCESSO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
