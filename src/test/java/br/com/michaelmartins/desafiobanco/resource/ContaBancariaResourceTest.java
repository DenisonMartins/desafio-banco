package br.com.michaelmartins.desafiobanco.resource;

import br.com.michaelmartins.desafiobanco.dto.ContaBancariaDTO;
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
import org.springframework.http.ResponseEntity;
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

        ContaBancariaDTO contaBancaria = contaBancariaResponse();
        BDDMockito.when(contaBancariaServiceMock.importar(eq(solicitacaoAberturaConta)))
                .thenReturn(contaBancaria);

        ResponseEntity<ContaBancariaDTO> resultado = resource.importar(solicitacaoAberturaConta);

        BDDMockito.verify(contaBancariaServiceMock).importar(eq(solicitacaoAberturaConta));
        assertThat(resultado).isNotNull();
    }

    @Test
    void depositar_DeveriaRealizarODeposito() {
        resource.depositar(1L, Double.parseDouble("125.00"));

        BDDMockito.verify(contaBancariaServiceMock).depositar(anyLong(), anyDouble());
    }

    @Test
    void sacar_ComValorDeSaqueMaiorQueLimite_DeveriaLancarExcecao() {
        BDDMockito.when(contaBancariaServiceMock.sacar(anyLong(), anyDouble()))
                .thenThrow(LimiteMaximoTransferenciaException.class);

        assertThrows(LimiteMaximoTransferenciaException.class, () -> resource.sacar(1L, 501.0));
    }

    @Test
    void sacar_ComValorDentroDoLimite_Sucesso() {
        BDDMockito.when(contaBancariaServiceMock.sacar(anyLong(), anyDouble()))
                .thenReturn(contaBancariaResponse());

        ResponseEntity<ContaBancariaDTO> resultado = resource.sacar(1L, 300.0);

        assertThat(resultado).isNotNull();
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

        ContaBancariaDTO contaBancariaDTO = contaBancariaResponse();
        BDDMockito.when(contaBancariaServiceMock.transferir(eq(solicitacaoTransferencia))).thenReturn(contaBancariaDTO);

        ResponseEntity<ContaBancariaDTO> resultado = resource.transferir(solicitacaoTransferencia);

        assertThat(resultado).isNotNull();
    }
}