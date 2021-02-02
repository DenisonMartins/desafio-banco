package br.com.michaelmartins.desafiobanco.fixture;

import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;

import java.math.BigDecimal;

public class SolicitacaoContaFixture {

    private static SolicitacaoConta umaInstanciaSolicitacaoContaComNomePessoa() {
        SolicitacaoConta solicitacaoConta = new SolicitacaoConta();
        solicitacaoConta.setNome("José");
        return solicitacaoConta;
    }

    public static SolicitacaoConta solicitacaoComSaldoInsuficiente() {
        return criarSolicitacao(BigDecimal.TEN);
    }

    public static SolicitacaoConta solicitacaoComSaldoSuficiente() {
        return criarSolicitacao(new BigDecimal("100.0"));
    }

    public static SolicitacaoConta criarSolicitacao(BigDecimal saldo) {
        SolicitacaoConta solicitacaoConta = umaInstanciaSolicitacaoContaComNomePessoa();
        solicitacaoConta.setCpf("58951449009");
        solicitacaoConta.setSaldo(saldo);
        return solicitacaoConta;
    }

    public static SolicitacaoConta solicitacaoComSaldoCpfVazio() {
        SolicitacaoConta solicitacaoConta = umaInstanciaSolicitacaoContaComNomePessoa();
        solicitacaoConta.setSaldo(new BigDecimal("100.0"));
        return solicitacaoConta;
    }

    public static SolicitacaoConta solicitacaoComSaldoCpfInvalido() {
        SolicitacaoConta solicitacaoConta = umaInstanciaSolicitacaoContaComNomePessoa();
        solicitacaoConta.setCpf("11111111111");
        solicitacaoConta.setSaldo(new BigDecimal("100.0"));
        return solicitacaoConta;
    }
}
