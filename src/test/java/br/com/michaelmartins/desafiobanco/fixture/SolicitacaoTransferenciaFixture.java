package br.com.michaelmartins.desafiobanco.fixture;

import br.com.michaelmartins.desafiobanco.dto.SolicitacaoTransferencia;

public class SolicitacaoTransferenciaFixture {

    private static SolicitacaoTransferencia umaSolicitacaoDeTransferencia() {
        SolicitacaoTransferencia solicitacaoTransferencia = new SolicitacaoTransferencia();
        solicitacaoTransferencia.setContaSolicitante("12345");
        solicitacaoTransferencia.setContaBeneficiario("6789");
        return solicitacaoTransferencia;
    }

    public static SolicitacaoTransferencia solicitacaoComValorSuperiorLimite() {
        SolicitacaoTransferencia solicitacaoTransferencia = new SolicitacaoTransferencia();
        solicitacaoTransferencia.setValorTransferencia(700.0);
        return solicitacaoTransferencia;
    }

    public static SolicitacaoTransferencia solicitacaoDeTransferenciaComValorSuperiorDoSaldoDaContaSolicitante() {
        SolicitacaoTransferencia solicitacaoTransferencia = umaSolicitacaoDeTransferencia();
        solicitacaoTransferencia.setValorTransferencia(400.0);
        return solicitacaoTransferencia;
    }

    public static SolicitacaoTransferencia solicitacaoDeTransferencia() {
        SolicitacaoTransferencia solicitacaoTransferencia = umaSolicitacaoDeTransferencia();
        solicitacaoTransferencia.setValorTransferencia(100.0);
        return solicitacaoTransferencia;
    }
}
