package br.com.michaelmartins.desafiobanco.service;

import br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ContaBancariaService {
    ContaBancariaResponse importar(SolicitacaoConta solicitacaoConta);

    ContaBancariaResponse depositar(Long id, String valorDeposito);

    ContaBancariaResponse sacar(Long id, String valorDeSaque);
}
