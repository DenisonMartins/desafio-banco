package br.com.michaelmartins.desafiobanco.dto;

import br.com.michaelmartins.desafiobanco.domain.ContaBancaria;

public class ContaBancariaResponse {

    public static final String CONTA_CADASTRADA_COM_SUCESSO = "Conta cadastrada com sucesso!";
    public static final String DEPOSITO_REALIZADO_COM_SUCESSO = "Depósito realizado com sucesso!";
    public static final String SAQUE_REALIZADO_COM_SUCESSO = "Saque realizado com sucesso!";
    public static final String TRANSFERENCIA_REALIZADA_COM_SUCESSO = "Transferência realizada com sucesso!";

    private Long id;
    private String numeroConta;
    private String message;
    private Double saldo;

    public ContaBancariaResponse(Long id, String numeroConta, String valor, String message) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.saldo = Double.parseDouble(valor);
        this.message = message;
    }

    public ContaBancariaResponse(ContaBancaria entity) {
        this.id = entity.getId();
        this.numeroConta = entity.getNumeroConta();
        this.saldo = entity.getSaldo();
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

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
