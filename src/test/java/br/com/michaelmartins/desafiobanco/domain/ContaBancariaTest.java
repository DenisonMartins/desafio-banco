package br.com.michaelmartins.desafiobanco.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContaBancariaTest {

    private ContaBancaria contaBancaria;

    @BeforeEach
    void setUp() {
        contaBancaria = new ContaBancaria();
    }

    @Test
    void adicionarValorAoSaldo() {
        contaBancaria.adicionarValorAoSaldo(200.0);

        assertThat(contaBancaria.getSaldo()).isEqualTo(200.0);
    }

    @Test
    void retirarValorDoSaldo() {
        contaBancaria.setSaldo(500.0);

        contaBancaria.retirarValorDoSaldo(200.0);

        assertThat(contaBancaria.getSaldo()).isEqualTo(300.0);
    }

    @Test
    void temValorDisponivelParaTransferencia_SaldoMaiorQueValorTransferencia_DeveRetonarTrue() {
        contaBancaria.setSaldo(500.0);

        boolean valorDisponivel = contaBancaria.temValorDisponivelParaTransferencia(200.0);

        assertThat(valorDisponivel).isTrue();
    }

    @Test
    void temValorDisponivelParaTransferencia_SaldoMenorQueValorTransferencia_DeveRetonarFalse() {
        contaBancaria.setSaldo(100.0);

        boolean valorDisponivel = contaBancaria.temValorDisponivelParaTransferencia(200.0);

        assertThat(valorDisponivel).isFalse();
    }
}