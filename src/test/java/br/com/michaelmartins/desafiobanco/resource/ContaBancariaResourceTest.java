package br.com.michaelmartins.desafiobanco.resource;

import br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoTransferencia;
import br.com.michaelmartins.desafiobanco.exception.LimiteMaximoTransferenciaException;
import br.com.michaelmartins.desafiobanco.exception.SaldoAberturaContaInsuficienteException;
import br.com.michaelmartins.desafiobanco.exception.SaldoIndisponivelException;
import br.com.michaelmartins.desafiobanco.service.ContaBancariaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static br.com.michaelmartins.desafiobanco.fixture.ContaBancariaResponseFixture.contaBancariaResponse;
import static br.com.michaelmartins.desafiobanco.fixture.SolicitacaoContaFixture.solicitacaoComSaldoInsuficiente;
import static br.com.michaelmartins.desafiobanco.fixture.SolicitacaoContaFixture.solicitacaoComSaldoSuficiente;
import static br.com.michaelmartins.desafiobanco.fixture.SolicitacaoTransferenciaFixture.solicitacaoComValorSuperiorLimite;
import static br.com.michaelmartins.desafiobanco.fixture.SolicitacaoTransferenciaFixture.solicitacaoDeTransferenciaComValorSuperiorDoSaldoDaContaSolicitante;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;

class ContaBancariaResourceTest {

    @InjectMocks private ContaBancariaResource resource;
    @Mock private ContaBancariaService contaBancariaServiceMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    void importar_SaldoInsuficiente_DeveriaLancarExcecao() {

        SolicitacaoConta solicitacaoAberturaConta = solicitacaoComSaldoInsuficiente();

        BDDMockito.when(contaBancariaServiceMock.importar(eq(solicitacaoAberturaConta)))
                .thenThrow(SaldoAberturaContaInsuficienteException.class);

        assertThrows(SaldoAberturaContaInsuficienteException.class, () -> resource.importar(solicitacaoAberturaConta));
    }

    @Test
    void importar_SaldoSuficiente_Sucesso() {

        SolicitacaoConta solicitacaoAberturaConta = solicitacaoComSaldoSuficiente();

        ContaBancariaResponse contaBancaria = contaBancariaResponse();
        BDDMockito.when(contaBancariaServiceMock.importar(eq(solicitacaoAberturaConta)))
                .thenReturn(contaBancaria);

        ContaBancariaResponse resultado = resource.importar(solicitacaoAberturaConta).getBody();

        assertThat(resultado)
                .extracting(ContaBancariaResponse::getId)
                .isEqualTo(contaBancaria.getId());
    }

    @Test
    void depositar() {
        BDDMockito.when(contaBancariaServiceMock.depositar(anyLong(), anyDouble())).thenReturn(contaBancariaResponse());

        ContaBancariaResponse contaBancariaResponse = resource.depositar(1L, Double.parseDouble("125.00")).getBody();

        assertationsContaBancaria(contaBancariaResponse);
    }

    @Test
    void sacar_ComValorDeSaqueMaiorQueLimite_DeveriaLancarExcecao() {
        Double valorSaque = Double.parseDouble("501");
        BDDMockito.when(contaBancariaServiceMock.sacar(anyLong(), eq(valorSaque)))
                .thenThrow(LimiteMaximoTransferenciaException.class);

        assertThrows(LimiteMaximoTransferenciaException.class, () -> resource.sacar(1L, valorSaque));
    }

    @Test
    void sacar_ComValorDentroDoLimite_Sucesso() {
        Double valorSaque = Double.parseDouble("300");
        BDDMockito.when(contaBancariaServiceMock.sacar(anyLong(), eq(valorSaque)))
                .thenReturn(contaBancariaResponse());

        ContaBancariaResponse contaBancariaResponse = resource.sacar(1L, valorSaque).getBody();

        assertationsContaBancaria(contaBancariaResponse);
    }

    @Test
    void transferir_ComValorSuperiorLimite_deveriaLancarExcecao() {
        SolicitacaoTransferencia solicitacaoTransferencia = solicitacaoComValorSuperiorLimite();
        BDDMockito.when(contaBancariaServiceMock.transferir(solicitacaoTransferencia))
                .thenThrow(LimiteMaximoTransferenciaException.class);

        assertThrows(LimiteMaximoTransferenciaException.class, () -> resource.transferir(solicitacaoTransferencia));
    }

    @Test
    void transferir_ComValorSuperiorAoSaldoContaSolicitante_deveriaLancarExcecao() {
        SolicitacaoTransferencia solicitacaoTransferencia = solicitacaoComValorSuperiorLimite();
        BDDMockito.when(contaBancariaServiceMock.transferir(solicitacaoTransferencia))
                .thenThrow(SaldoIndisponivelException.class);

        assertThrows(SaldoIndisponivelException.class, () -> resource.transferir(solicitacaoTransferencia));
    }

    @Test
    void transferir_SaldoAdequadoDoSolicitante_Sucesso() {
        SolicitacaoTransferencia solicitacaoTransferencia = solicitacaoDeTransferenciaComValorSuperiorDoSaldoDaContaSolicitante();

        ContaBancariaResponse contaBancariaResponse = contaBancariaResponse();
        BDDMockito.when(contaBancariaServiceMock.transferir(eq(solicitacaoTransferencia))).thenReturn(contaBancariaResponse);

        ContaBancariaResponse resultado = resource.transferir(solicitacaoTransferencia).getBody();

        assertationsContaBancaria(resultado);
    }

    private void assertationsContaBancaria(ContaBancariaResponse contaBancariaResponse) {
        assertThat(contaBancariaResponse)
                .extracting(ContaBancariaResponse::getSaldo)
                .isNotNull();
    }
}