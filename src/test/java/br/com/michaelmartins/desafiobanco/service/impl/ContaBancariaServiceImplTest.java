package br.com.michaelmartins.desafiobanco.service.impl;

import br.com.michaelmartins.desafiobanco.domain.ContaBancaria;
import br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import br.com.michaelmartins.desafiobanco.fixture.ContaBancariaFixture;
import br.com.michaelmartins.desafiobanco.repository.ContaBancariaRepository;
import br.com.michaelmartins.desafiobanco.service.GeradorNumeroConta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.michaelmartins.desafiobanco.fixture.ContaBancariaFixture.contaBancariaSalva;
import static br.com.michaelmartins.desafiobanco.fixture.SolicitacaoContaFixture.solicitacaoComSaldoSuficiente;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ContaBancariaServiceImplTest {

    @InjectMocks private ContaBancariaServiceImpl service;
    @Mock private ContaBancariaRepository repositoryMock;
    @Mock private GeradorNumeroConta geradorNumeroContaMock;

    private ContaBancaria contaBancaria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contaBancaria = contaBancariaSalva();
        BDDMockito.when(repositoryMock.save(any())).thenReturn(contaBancaria);
    }

    @Test
    void importar_ChamadaAOutroComponente_VerificaQueFoiChamado() {
        SolicitacaoConta solicitacaoConta = solicitacaoComSaldoSuficiente();

        service.importar(solicitacaoConta);

        verify(geradorNumeroContaMock).gerar();
    }

    @Test
    void importar_deveriaCriarAConta_Sucesso() {
        SolicitacaoConta solicitacaoConta = solicitacaoComSaldoSuficiente();

        String numeroConta = "123";
        when(geradorNumeroContaMock.gerar()).thenReturn(numeroConta);

        ContaBancariaResponse resultado = service.importar(solicitacaoConta);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(contaBancaria.getId());
        assertThat(resultado.getNumeroConta()).isEqualTo(numeroConta);
    }

    @Test
    void depositar() {
        BDDMockito.when(repositoryMock.getOne(anyLong())).thenReturn(contaBancariaSalva());
        ContaBancaria contaBancariaAtualizada = ContaBancariaFixture.contaBancariaAtualizada();
        BDDMockito.when(repositoryMock.save(any())).thenReturn(contaBancariaAtualizada);

        ContaBancariaResponse resultado = service.depositar(1L, "100.0");

        assertThat(resultado).isNotNull();
        assertThat(resultado.getSaldo()).isEqualTo(250.0);
    }
}