package br.com.michaelmartins.desafiobanco.resource;

import br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import br.com.michaelmartins.desafiobanco.service.ImportarContaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

@RestController
@RequestMapping("contas-bancarias")
@Slf4j
public class ImportarContaResource {

    private final ImportarContaService importarContaService;

    public ImportarContaResource(ImportarContaService importarContaService) {
        this.importarContaService = importarContaService;
    }

    @PostMapping(path = "/criar")
    public ResponseEntity<ContaBancariaResponse> importar(@RequestBody @Valid SolicitacaoConta solicitacaoConta) {
        log.info("Solicitação de abertura de conta: {}", solicitacaoConta);
        ContaBancariaResponse contaBancaria = importarContaService.importar(solicitacaoConta);
        URI uri = fromCurrentRequestUri().path("/{id}").buildAndExpand(contaBancaria.getId()).toUri();
        return ResponseEntity.created(uri).body(contaBancaria);
    }
}
