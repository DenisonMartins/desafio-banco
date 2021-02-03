package br.com.michaelmartins.desafiobanco.service;

import br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoTransferencia;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ContaBancariaService {
    ContaBancariaResponse importar(SolicitacaoConta solicitacaoConta);

    ContaBancariaResponse depositar(Long id, Double valorDeposito);

    ContaBancariaResponse sacar(Long id, Double valorDeSaque);

    ContaBancariaResponse transferir(SolicitacaoTransferencia solicitacaoTransferencia);
}
