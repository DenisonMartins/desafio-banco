package br.com.michaelmartins.desafiobanco.service.impl;

import br.com.michaelmartins.desafiobanco.domain.ContaBancaria;
import br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import br.com.michaelmartins.desafiobanco.repository.ContaBancariaRepository;
import br.com.michaelmartins.desafiobanco.service.GeradorNumeroConta;
import br.com.michaelmartins.desafiobanco.service.ImportarContaService;
import org.springframework.stereotype.Service;

@Service
public class ImportarContaServiceImpl implements ImportarContaService {

    private final ContaBancariaRepository repository;
    private final GeradorNumeroConta gerador;

    public ImportarContaServiceImpl(ContaBancariaRepository repository, GeradorNumeroConta gerador) {
        this.repository = repository;
        this.gerador = gerador;
    }

    @Override
    public ContaBancariaResponse importar(SolicitacaoConta solicitacaoConta) {
        ContaBancaria contaBancaria = new ContaBancaria(solicitacaoConta);
        contaBancaria.setNumeroConta(gerador.gerar());
        return new ContaBancariaResponse(repository.save(contaBancaria));
    }
}
