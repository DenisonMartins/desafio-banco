package br.com.michaelmartins.desafiobanco.resource;

import br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import br.com.michaelmartins.desafiobanco.exception.SaldoAberturaContaInsuficienteException;
import br.com.michaelmartins.desafiobanco.fixture.ContaBancariaResponseFixture;
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

import java.math.BigDecimal;

import static br.com.michaelmartins.desafiobanco.fixture.ContaBancariaResponseFixture.contaBancariaResponse;
import static br.com.michaelmartins.desafiobanco.fixture.SolicitacaoContaFixture.solicitacaoComSaldoInsuficiente;
import static br.com.michaelmartins.desafiobanco.fixture.SolicitacaoContaFixture.solicitacaoComSaldoSuficiente;
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
        BDDMockito.when(contaBancariaServiceMock.depositar(anyLong(), anyString())).thenReturn(contaBancariaResponse());

        ContaBancariaResponse contaBancariaResponse = resource.depositar(1L, "125.00").getBody();

        assertThat(contaBancariaResponse)
                .extracting(ContaBancariaResponse::getSaldo)
                .isEqualTo(new BigDecimal("1500"));
    }
}