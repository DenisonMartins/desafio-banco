package br.com.michaelmartins.desafiobanco.resource;

import br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import br.com.michaelmartins.desafiobanco.exception.SaldoAberturaContaInsuficienteException;
import br.com.michaelmartins.desafiobanco.service.ImportarContaService;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

class ImportarContaResourceTest {

    @InjectMocks private ImportarContaResource resource;
    @Mock private ImportarContaService importarContaServiceMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    void importar_SaldoInsuficiente_DeveriaLancarExcecao() {

        SolicitacaoConta solicitacaoAberturaConta = solicitacaoComSaldoInsuficiente();

        BDDMockito.when(importarContaServiceMock.importar(eq(solicitacaoAberturaConta)))
                .thenThrow(SaldoAberturaContaInsuficienteException.class);

        assertThrows(SaldoAberturaContaInsuficienteException.class, () -> resource.importar(solicitacaoAberturaConta));
    }

    @Test
    void importar_SaldoSuficiente_Sucesso() {

        SolicitacaoConta solicitacaoAberturaConta = solicitacaoComSaldoSuficiente();

        ContaBancariaResponse contaBancaria = contaBancariaResponse();
        BDDMockito.when(importarContaServiceMock.importar(eq(solicitacaoAberturaConta)))
                .thenReturn(contaBancaria);

        ContaBancariaResponse resultado = resource.importar(solicitacaoAberturaConta).getBody();

        assertThat(resultado)
                .extracting(ContaBancariaResponse::getId)
                .isEqualTo(contaBancaria.getId());
    }
}