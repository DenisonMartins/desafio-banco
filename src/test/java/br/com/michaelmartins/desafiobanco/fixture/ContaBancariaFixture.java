package br.com.michaelmartins.desafiobanco.fixture;

import br.com.michaelmartins.desafiobanco.domain.ContaBancaria;
import br.com.michaelmartins.desafiobanco.domain.Pessoa;

import java.math.BigDecimal;

public class ContaBancariaFixture {

    public static ContaBancaria contaBancariaSalva() {
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setId(1L);
        contaBancaria.setPessoa(new Pessoa("José", "12345678912"));
        contaBancaria.setNumeroConta("123");
        contaBancaria.setSaldo(new BigDecimal("150.0"));
        return contaBancaria;
    }
}
