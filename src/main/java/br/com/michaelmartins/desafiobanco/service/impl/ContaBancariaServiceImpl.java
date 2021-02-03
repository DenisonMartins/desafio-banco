package br.com.michaelmartins.desafiobanco.service.impl;

import br.com.michaelmartins.desafiobanco.domain.ContaBancaria;
import br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import br.com.michaelmartins.desafiobanco.exception.LimiteMaximoTransferenciaException;
import br.com.michaelmartins.desafiobanco.repository.ContaBancariaRepository;
import br.com.michaelmartins.desafiobanco.service.ContaBancariaService;
import br.com.michaelmartins.desafiobanco.service.GeradorNumeroConta;
import org.springframework.stereotype.Service;

import static br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse.CONTA_CADASTRADA_COM_SUCESSO;
import static br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse.DEPOSITO_REALIZADO_COM_SUCESSO;

@Service
public class ContaBancariaServiceImpl implements ContaBancariaService {

    public static final int LIMITE_MAXIMO_TRANSFERENCIA = 500;

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
    public ContaBancariaResponse depositar(Long id, String valorDeposito) {
        ContaBancaria contaRecuperada = recuperaContaBancaria(id);
        contaRecuperada.adicionarValorAoSaldo(valorDeposito);

        return getContaBancariaResponse(contaRecuperada, DEPOSITO_REALIZADO_COM_SUCESSO);
    }

    private ContaBancaria recuperaContaBancaria(Long id) {
        return repository.getOne(id);
    }

    @Override
    public ContaBancariaResponse sacar(Long id, String valorDeSaque) {
        verificaValorDeSaqueDentroDoLimiteDeTransferencia(valorDeSaque);
        ContaBancaria contaBancaria = recuperaContaBancaria(id);
        contaBancaria.retirarValorDoSaldo(valorDeSaque);
        return getContaBancariaResponse(contaBancaria, ContaBancariaResponse.SAQUE_REALIZADO_COM_SUCESSO);
    }

    private void verificaValorDeSaqueDentroDoLimiteDeTransferencia(String valorDeSaque) {
        if (isValorMaiorQueLimite(valorDeSaque)) {
            throw new LimiteMaximoTransferenciaException("Operação de transferência tem um limite máximo de 500 por operação.");
        }
    }

    private boolean isValorMaiorQueLimite(String valorDeSaque) {
        return Double.parseDouble(valorDeSaque) > LIMITE_MAXIMO_TRANSFERENCIA;
    }

    private ContaBancariaResponse getContaBancariaResponse(ContaBancaria contaBancaria, String mensagem) {
        ContaBancaria contaBancariaSalva = salvar(contaBancaria);
        ContaBancariaResponse contaBancariaResponse = new ContaBancariaResponse(contaBancariaSalva);
        contaBancariaResponse.setMessage(mensagem);
        return contaBancariaResponse;
    }

    private ContaBancaria salvar(ContaBancaria contaBancaria) {
        return repository.save(contaBancaria);
    }
}
