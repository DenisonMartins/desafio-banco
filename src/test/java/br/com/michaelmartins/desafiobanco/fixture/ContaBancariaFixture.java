package br.com.michaelmartins.desafiobanco.fixture;

import br.com.michaelmartins.desafiobanco.domain.ContaBancaria;
import br.com.michaelmartins.desafiobanco.domain.Pessoa;

public class ContaBancariaFixture {

    private static ContaBancaria umaContaBancaria() {
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setId(1L);
        contaBancaria.setPessoa(new Pessoa("José", "12345678912"));
        return contaBancaria;
    }

    public static ContaBancaria contaBancariaSalva() {
        ContaBancaria contaBancaria = umaContaBancaria();
        contaBancaria.setNumeroConta("123");
        contaBancaria.setSaldo(150.0);
        return contaBancaria;
    }

    public static ContaBancaria contaBancariaBeneficiario() {
        ContaBancaria contaBancaria = umaContaBancaria();
        contaBancaria.setNumeroConta("456");
        contaBancaria.setSaldo(300.0);
        return contaBancaria;
    }

    public static ContaBancaria contaBancariaBeneficiarioAtualizada() {
        ContaBancaria contaBancaria = umaContaBancaria();
        contaBancaria.setNumeroConta("789");
        contaBancaria.setSaldo(400.0);
        return contaBancaria;
    }

    public static ContaBancaria contaBancariaSolicitanteAtualizada() {
        ContaBancaria contaBancaria = umaContaBancaria();
        contaBancaria.setNumeroConta("159");
        contaBancaria.setSaldo(50.0);
        return contaBancaria;
    }

    public static ContaBancaria contaBancariaAtualizadaComDeposito() {
        ContaBancaria contaBancaria = umaContaBancaria();
        contaBancaria.setNumeroConta("357");
        contaBancaria.setSaldo(250.0);
        return contaBancaria;
    }

    public static ContaBancaria contaBancariaAtualizadaComSaque() {
        ContaBancaria contaBancaria = umaContaBancaria();
        contaBancaria.setNumeroConta("987");
        contaBancaria.setSaldo(100.0);
        return contaBancaria;
    }
}
