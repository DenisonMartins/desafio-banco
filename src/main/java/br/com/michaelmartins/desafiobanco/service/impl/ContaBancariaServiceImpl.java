package br.com.michaelmartins.desafiobanco.service.impl;

import br.com.michaelmartins.desafiobanco.domain.ContaBancaria;
import br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import br.com.michaelmartins.desafiobanco.repository.ContaBancariaRepository;
import br.com.michaelmartins.desafiobanco.service.ContaBancariaService;
import br.com.michaelmartins.desafiobanco.service.GeradorNumeroConta;
import org.springframework.stereotype.Service;

import static br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse.CONTA_CADASTRADA_COM_SUCESSO;
import static br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse.DEPOSITO_REALIZADO_COM_SUCESSO;

@Service
public class ContaBancariaServiceImpl implements ContaBancariaService {

    private final ContaBancariaRepository repository;
    private final GeradorNumeroConta gerador;

    public ContaBancariaServiceImpl(ContaBancariaRepository repository, GeradorNumeroConta gerador) {
        this.repository = repository;
        this.gerador = gerador;
    }

    @Override
    public ContaBancariaResponse importar(SolicitacaoConta solicitacaoConta) {
        ContaBancaria contaBancaria = new ContaBancaria(solicitacaoConta);
        contaBancaria.setNumeroConta(gerador.gerar());

        return getContaBancariaResponse(contaBancaria, CONTA_CADASTRADA_COM_SUCESSO);
    }

    @Override
    public ContaBancariaResponse depositar(Long id, String valor) {
        ContaBancaria contaRecuperada = repository.getOne(id);
        contaRecuperada.adicionarValorAoSaldo(valor);

        return getContaBancariaResponse(contaRecuperada, DEPOSITO_REALIZADO_COM_SUCESSO);
    }

    private ContaBancariaResponse getContaBancariaResponse(ContaBancaria contaBancaria, String mensagem) {
        ContaBancariaResponse contaBancariaResponse = new ContaBancariaResponse(salvar(contaBancaria));
        contaBancariaResponse.setMessage(mensagem);
        return contaBancariaResponse;
    }

    private ContaBancaria salvar(ContaBancaria contaBancaria) {
        return repository.save(contaBancaria);
    }
}
