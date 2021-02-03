package br.com.michaelmartins.desafiobanco.service.impl;

import br.com.michaelmartins.desafiobanco.domain.ContaBancaria;
import br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoTransferencia;
import br.com.michaelmartins.desafiobanco.exception.LimiteMaximoTransferenciaException;
import br.com.michaelmartins.desafiobanco.exception.SaldoIndisponivelException;
import br.com.michaelmartins.desafiobanco.repository.ContaBancariaRepository;
import br.com.michaelmartins.desafiobanco.service.ContaBancariaService;
import br.com.michaelmartins.desafiobanco.service.GeradorNumeroConta;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse.*;
import static br.com.michaelmartins.desafiobanco.exception.LimiteMaximoTransferenciaException.MESSAGE_VALOR_LIMITE_TRANSFERENCIA;
import static br.com.michaelmartins.desafiobanco.exception.SaldoIndisponivelException.SALDO_INSUFICIENTE;

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
    public ContaBancariaResponse depositar(Long id, Double valorDeposito) {
        ContaBancaria contaRecuperada = recuperaContaBancaria(id);
        contaRecuperada.adicionarValorAoSaldo(valorDeposito);

        return getContaBancariaResponse(contaRecuperada, DEPOSITO_REALIZADO_COM_SUCESSO);
    }

    private ContaBancaria recuperaContaBancaria(Long id) {
        return repository.getOne(id);
    }

    @Override
    public ContaBancariaResponse sacar(Long id, Double valorDeSaque) {
        verificaSeValorEstaDentroDoLimiteDeTransferencia(valorDeSaque);
        ContaBancaria contaBancaria = recuperaContaBancaria(id);
        contaBancaria.retirarValorDoSaldo(valorDeSaque);
        return getContaBancariaResponse(contaBancaria, SAQUE_REALIZADO_COM_SUCESSO);
    }

    @Override
    public ContaBancariaResponse transferir(SolicitacaoTransferencia solicitacaoTransferencia) {
        verificaSeValorEstaDentroDoLimiteDeTransferencia(solicitacaoTransferencia.getValorTransferencia());

        ContaBancaria contaSolicitante = verificaSeHaValorDisponivelNaContaSolicitante(solicitacaoTransferencia);

        executarTransferencia(solicitacaoTransferencia, contaSolicitante);

        return mapearRetornodaContaBancaria(contaSolicitante, TRANSFERENCIA_REALIZADA_COM_SUCESSO);
    }

    private ContaBancariaResponse mapearRetornodaContaBancaria(ContaBancaria contaSolicitante, String mensagem) {
        ContaBancariaResponse contaBancariaResponse = new ContaBancariaResponse(contaSolicitante);
        contaBancariaResponse.setMessage(mensagem);
        return contaBancariaResponse;
    }

    private void executarTransferencia(SolicitacaoTransferencia solicitacaoTransferencia, ContaBancaria contaSolicitante) {
        contaSolicitante.retirarValorDoSaldo(solicitacaoTransferencia.getValorTransferencia());

        ContaBancaria contaBeneficiario = repository.findByNumeroConta(solicitacaoTransferencia.getContaBeneficiario());
        contaBeneficiario.adicionarValorAoSaldo(solicitacaoTransferencia.getValorTransferencia());

        repository.saveAll(Arrays.asList(contaSolicitante, contaBeneficiario));
    }

    private ContaBancaria verificaSeHaValorDisponivelNaContaSolicitante(SolicitacaoTransferencia solicitacaoTransferencia) {
        ContaBancaria contaSolicitante = repository.findByNumeroConta(solicitacaoTransferencia.getContaSolicitante());
        if (contaSolicitante.temValorDisponivelParaTransferencia(solicitacaoTransferencia.getValorTransferencia())) {
            return contaSolicitante;
        }
        throw new SaldoIndisponivelException(SALDO_INSUFICIENTE);
    }

    private void verificaSeValorEstaDentroDoLimiteDeTransferencia(Double valor) {
        if (isValorMaiorQueLimite(valor)) {
            throw new LimiteMaximoTransferenciaException(MESSAGE_VALOR_LIMITE_TRANSFERENCIA);
        }
    }

    private boolean isValorMaiorQueLimite(Double valorDeSaque) {
        return valorDeSaque > LIMITE_MAXIMO_TRANSFERENCIA;
    }

    private ContaBancariaResponse getContaBancariaResponse(ContaBancaria contaBancaria, String mensagem) {
        ContaBancaria contaBancariaSalva = salvar(contaBancaria);
        return mapearRetornodaContaBancaria(contaBancariaSalva, mensagem);
    }

    private ContaBancaria salvar(ContaBancaria contaBancaria) {
        return repository.save(contaBancaria);
    }
}
