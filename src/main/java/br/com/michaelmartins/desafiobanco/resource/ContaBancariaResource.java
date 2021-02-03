package br.com.michaelmartins.desafiobanco.resource;

import br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoTransferencia;
import br.com.michaelmartins.desafiobanco.service.ContaBancariaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

@RestController
@RequestMapping("contas-bancarias")
@Slf4j
public class ContaBancariaResource {

    private final ContaBancariaService contaBancariaService;

    public ContaBancariaResource(ContaBancariaService contaBancariaService) {
        this.contaBancariaService = contaBancariaService;
    }

    @PostMapping(path = "/criar")
    public ResponseEntity<ContaBancariaResponse> importar(@RequestBody @Valid SolicitacaoConta solicitacaoConta) {
        log.info("Solicitação de abertura de conta: {}", solicitacaoConta);
        ContaBancariaResponse contaBancaria = contaBancariaService.importar(solicitacaoConta);
        URI uri = fromCurrentRequestUri().path("/{id}").buildAndExpand(contaBancaria.getId()).toUri();
        return ResponseEntity.created(uri).body(contaBancaria);
    }

    @PutMapping(path = "/depositar/{id}")
    public ResponseEntity<ContaBancariaResponse> depositar(@PathVariable Long id, @RequestBody Double valor) {
        log.info("Saldo a depositar: {}", valor);
        ContaBancariaResponse contaBancaria = contaBancariaService.depositar(id, valor);
        return ResponseEntity.ok(contaBancaria);
    }

    @PutMapping(path = "/saque/{id}")
    public ResponseEntity<ContaBancariaResponse> sacar(@PathVariable Long id, @RequestBody Double valor) {
        log.info("Realização de saque no valor: {}", valor);
        ContaBancariaResponse contaBancaria = contaBancariaService.sacar(id, valor);
        return ResponseEntity.ok(contaBancaria);
    }

    @PostMapping(path = "/transferencia")
    public ResponseEntity<ContaBancariaResponse> transferir(@RequestBody SolicitacaoTransferencia solicitacaoTransferencia) {
        log.info("Solicitação de transferência: {}", solicitacaoTransferencia);
        ContaBancariaResponse contaBancaria = contaBancariaService.transferir(solicitacaoTransferencia);
        return ResponseEntity.ok(contaBancaria);
    }
}
