package br.com.michaelmartins.desafiobanco.domain;

import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tb_conta_bancaria")
public class ContaBancaria {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private Pessoa pessoa;
    private String numeroConta;
    private Double saldo;

    public ContaBancaria() {
    }

    public ContaBancaria(Map<String, String> mapaConta) {
        this.numeroConta = mapaConta.get("Numero Conta");
        this.saldo = Double.parseDouble(mapaConta.get("Saldo"));
    }

    public ContaBancaria(SolicitacaoConta solicitacaoConta) {
        this.pessoa = new Pessoa(solicitacaoConta.getNome(), solicitacaoConta.getCpf());
        this.saldo = solicitacaoConta.getSaldo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void adicionarValorAoSaldo(String valorDeposito) {
        saldo += Double.parseDouble(valorDeposito);
    }

    public void retirarValorDoSaldo(String valorDeSaque) {
        saldo -= Double.parseDouble(valorDeSaque);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContaBancaria that = (ContaBancaria) o;
        return id.equals(that.id) && numeroConta.equals(that.numeroConta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroConta);
    }

    @Override
    public String toString() {
        return "ContaBancaria{" +
                "id=" + id +
                ", pessoa=" + pessoa +
                ", numeroConta='" + numeroConta + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
