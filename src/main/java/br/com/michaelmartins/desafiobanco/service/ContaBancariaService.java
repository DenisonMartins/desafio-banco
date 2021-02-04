package br.com.michaelmartins.desafiobanco.service;

import br.com.michaelmartins.desafiobanco.dto.ContaBancariaDTO;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoTransferencia;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ContaBancariaService {
    ContaBancariaDTO importar(SolicitacaoConta solicitacaoConta);

    ContaBancariaDTO depositar(Long id, Double valorDeposito);

    ContaBancariaDTO sacar(Long id, Double valorDeSaque);

    ContaBancariaDTO transferir(SolicitacaoTransferencia solicitacaoTransferencia);
}
