package br.com.michaelmartins.desafiobanco.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

class GeradorNumeroContaImplTest {

    @InjectMocks private GeradorNumeroContaImpl geradorNumeroConta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void gerar_GeraONumeroDaContaRandomicamente() {
        String numeroConta = geradorNumeroConta.gerar();

        assertThat(numeroConta).isNotNull();

    }
}