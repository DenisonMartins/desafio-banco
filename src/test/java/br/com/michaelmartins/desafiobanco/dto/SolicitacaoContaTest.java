package br.com.michaelmartins.desafiobanco.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static br.com.michaelmartins.desafiobanco.fixture.SolicitacaoContaFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

class SolicitacaoContaTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void getSaldo_SaldoInsuficiente() {
        SolicitacaoConta solicitacao = solicitacaoComSaldoInsuficiente();
        Set<ConstraintViolation<SolicitacaoConta>> constraintViolations = validator.validate(solicitacao);

        assertThat(constraintViolations).hasSize(1)
            .extracting(ConstraintViolation::getMessage)
            .contains("Saldo insuficiente para abertura de nova conta.");
    }

    @Test
    void getSaldo_SaldoSuficiente_Sucesso() {
        SolicitacaoConta solicitacao = solicitacaoComSaldoSuficiente();
        Set<ConstraintViolation<SolicitacaoConta>> constraintViolations = validator.validate(solicitacao);

        assertThat(constraintViolations).isEmpty();
    }

    @Test
    void getCpf_CpfVazio() {
        SolicitacaoConta solicitacao = solicitacaoComSaldoCpfVazio();
        Set<ConstraintViolation<SolicitacaoConta>> constraintViolations = validator.validate(solicitacao);

        assertThat(constraintViolations).hasSize(1)
            .extracting(ConstraintViolation::getMessage)
            .contains("É necessário informar um cpf para abertura de nova conta.");
    }

    @Test
    void getCpf_CpfInvalido() {
        SolicitacaoConta solicitacao = solicitacaoComSaldoCpfInvalido();
        Set<ConstraintViolation<SolicitacaoConta>> constraintViolations = validator.validate(solicitacao);

        assertThat(constraintViolations).hasSize(1)
                .extracting(ConstraintViolation::getMessage)
                .contains("CPF informado para criação de conta está inválido.");
    }

    @Test
    void getCpf_Valido() {
        SolicitacaoConta solicitacao = criarSolicitacao(new BigDecimal("200.0"));
        Set<ConstraintViolation<SolicitacaoConta>> constraintViolations = validator.validate(solicitacao);

        assertThat(constraintViolations).isEmpty();
    }
}