package br.com.michaelmartins.desafiobanco.dto;

import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Map;

public class SolicitacaoConta {

    private String nome;

    @NotEmpty(message = "É necessário informar um cpf para abertura de nova conta.")
    @CPF(message = "CPF informado para criação de conta está inválido.")
    private String cpf;

    @Min(value = 50, message = "Saldo insuficiente para abertura de nova conta.")
    private BigDecimal saldo;

    public SolicitacaoConta() {
    }

    public SolicitacaoConta(Map<String, String> mapSolicitacao) {
        this.nome = mapSolicitacao.get("Nome");
        this.cpf = mapSolicitacao.get("Cpf");
        this.saldo = new BigDecimal(mapSolicitacao.get("Saldo"));
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "SolicitacaoConta{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
