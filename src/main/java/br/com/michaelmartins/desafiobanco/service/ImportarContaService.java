package br.com.michaelmartins.desafiobanco.service;

import br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;

public interface ImportarContaService {
    ContaBancariaResponse importar(SolicitacaoConta solicitacaoConta);
}
