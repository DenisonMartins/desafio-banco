package br.com.michaelmartins.desafiobanco.fixture;

import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;

public class SolicitacaoContaFixture {

    private static SolicitacaoConta umaInstanciaSolicitacaoContaComNomePessoa() {
        SolicitacaoConta solicitacaoConta = new SolicitacaoConta();
        solicitacaoConta.setNome("José");
        return solicitacaoConta;
    }

    public static SolicitacaoConta solicitacaoComSaldoInsuficiente() {
        return criarSolicitacao(10.0);
    }

    public static SolicitacaoConta solicitacaoComSaldoSuficiente() {
        return criarSolicitacao(100.0);
    }

    public static SolicitacaoConta criarSolicitacao(Double saldo) {
        SolicitacaoConta solicitacaoConta = umaInstanciaSolicitacaoContaComNomePessoa();
        solicitacaoConta.setCpf("58951449009");
        solicitacaoConta.setSaldo(saldo);
        return solicitacaoConta;
    }

    public static SolicitacaoConta solicitacaoComSaldoCpfVazio() {
        SolicitacaoConta solicitacaoConta = umaInstanciaSolicitacaoContaComNomePessoa();
        solicitacaoConta.setSaldo(100.0);
        return solicitacaoConta;
    }

    public static SolicitacaoConta solicitacaoComSaldoCpfInvalido() {
        SolicitacaoConta solicitacaoConta = umaInstanciaSolicitacaoContaComNomePessoa();
        solicitacaoConta.setCpf("11111111111");
        solicitacaoConta.setSaldo(100.0);
        return solicitacaoConta;
    }
}
