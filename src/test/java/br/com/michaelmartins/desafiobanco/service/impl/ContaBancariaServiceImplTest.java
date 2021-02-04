package br.com.michaelmartins.desafiobanco.service.impl;

import br.com.michaelmartins.desafiobanco.domain.ContaBancaria;
import br.com.michaelmartins.desafiobanco.dto.ContaBancariaDTO;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoTransferencia;
import br.com.michaelmartins.desafiobanco.exception.LimiteMaximoTransferenciaException;
import br.com.michaelmartins.desafiobanco.exception.SaldoIndisponivelException;
import br.com.michaelmartins.desafiobanco.repository.ContaBancariaRepository;
import br.com.michaelmartins.desafiobanco.service.GeradorNumeroConta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.michaelmartins.desafiobanco.fixture.ContaBancariaFixture.*;
import static br.com.michaelmartins.desafiobanco.fixture.SolicitacaoContaFixture.solicitacaoComSaldoSuficiente;
import static br.com.michaelmartins.desafiobanco.fixture.SolicitacaoTransferenciaFixture.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
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
        BDDMockito.when(repositoryMock.getOne(anyLong())).thenReturn(contaBancariaSalva());
    }

    @Test
    void importar_DeveriaGerarUmNumeroDeConta() {
        SolicitacaoConta solicitacaoConta = solicitacaoComSaldoSuficiente();

        service.importar(solicitacaoConta);

        verify(geradorNumeroContaMock).gerar();
    }

    @Test
    void importar_deveriaCriarAConta_Sucesso() {
        SolicitacaoConta solicitacaoConta = solicitacaoComSaldoSuficiente();

        String numeroConta = "123";
        when(geradorNumeroContaMock.gerar()).thenReturn(numeroConta);

        ContaBancariaDTO resultado = service.importar(solicitacaoConta);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(contaBancaria.getId());
        assertThat(resultado.getNumeroConta()).isEqualTo(numeroConta);
    }

    @Test
    void depositar_deveriaSomarOvalorSolicitadoComOSaldoDaConta() {

        ContaBancaria contaBancariaAtualizada = contaBancariaAtualizadaComDeposito();
        BDDMockito.when(repositoryMock.save(any())).thenReturn(contaBancariaAtualizada);

        ContaBancariaDTO resultado = service.depositar(1L, Double.parseDouble("100.0"));

        assertationsContaBancaria(resultado, 250.0);
    }

    @Test
    void sacar_ComValorDeSaqueMaiorQueLimite_DeveriaLancarExcecao() {
        assertThrows(LimiteMaximoTransferenciaException.class, () -> service.sacar(1L, Double.parseDouble("501")));
    }

    @Test
    void sacar_ComValorDentroDoLimite_Sucesso() {
        Double valorSaque = Double.parseDouble("50");

        ContaBancaria contaBancariaAtualizada = contaBancariaAtualizadaComSaque();
        BDDMockito.when(repositoryMock.save(any())).thenReturn(contaBancariaAtualizada);

        ContaBancariaDTO contaBancariaDTO = service.sacar(1L, valorSaque);

        assertationsContaBancaria(contaBancariaDTO, 100.0);
    }

    @Test
    void transferir_ComValorSuperiorLimite_deveriaLancarExcecao() {
        SolicitacaoTransferencia solicitacaoTransferencia = solicitacaoComValorSuperiorLimite();

        assertThrows(LimiteMaximoTransferenciaException.class, () -> service.transferir(solicitacaoTransferencia));
    }

    @Test
    void transferir_ComValorSuperiorAoSaldoContaSolicitante_deveriaLancarExcecao() {
        SolicitacaoTransferencia solicitacaoTransferencia =
                solicitacaoDeTransferenciaComValorSuperiorDoSaldoDaContaSolicitante();

        BDDMockito.when(repositoryMock.findByNumeroConta(eq(solicitacaoTransferencia.getContaSolicitante())))
                .thenReturn(contaBancariaSalva());

        assertThrows(SaldoIndisponivelException.class, () -> service.transferir(solicitacaoTransferencia));
    }

    @Test
    void transferir_SaldoAdequadoDoSolicitante_Sucesso() {
        SolicitacaoTransferencia solicitacaoTransferencia = solicitacaoDeTransferencia();

        BDDMockito.when(repositoryMock.findByNumeroConta(eq(solicitacaoTransferencia.getContaSolicitante())))
                .thenReturn(contaBancariaSalva());

        BDDMockito.when(repositoryMock.findByNumeroConta(eq(solicitacaoTransferencia.getContaBeneficiario())))
                .thenReturn(contaBancariaBeneficiario());

        BDDMockito.when(repositoryMock.saveAll(anyList()))
                .thenReturn(asList(contaBancariaBeneficiarioAtualizada(), contaBancariaSolicitanteAtualizada()));

        ContaBancariaDTO contaBancariaDTO = service.transferir(solicitacaoTransferencia);

        assertThat(contaBancariaDTO)
                .extracting(ContaBancariaDTO::getSaldo)
                .isEqualTo(contaBancariaSolicitanteAtualizada().getSaldo());
    }

    private void assertationsContaBancaria(ContaBancariaDTO resultado, Double valorSaldo) {
        assertThat(resultado).isNotNull();
        assertThat(resultado.getSaldo()).isEqualTo(valorSaldo);
    }
}