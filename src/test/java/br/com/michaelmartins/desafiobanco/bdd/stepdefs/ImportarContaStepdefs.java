package br.com.michaelmartins.desafiobanco.bdd.stepdefs;

import br.com.michaelmartins.desafiobanco.domain.ContaBancaria;
import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import br.com.michaelmartins.desafiobanco.repository.ContaBancariaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.Pt;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImportarContaStepdefs extends StepDefs implements Pt {

    public static final String URL_DEFAULT_CONTAS_BANCARIAS = "/contas-bancarias";
    private String valorSaque;
    private String saldoADepositar;

    private void setup(ContaBancariaRepository contaBancariaRepository) {
        contaBancariaRepository.deleteAllInBatch();
    }

    public ImportarContaStepdefs(MockMvc mockMvc, ContaBancariaRepository contaBancariaRepository) {

        setup(contaBancariaRepository);

        List<SolicitacaoConta> solicitacoes = new ArrayList<>();
        List<ContaBancaria> contas = new ArrayList<>();

        Dado("que seja solicitada a criação de uma nova conta com os seguintes valores", (DataTable dataTable) ->
                dataTable.asMaps().stream().map(SolicitacaoConta::new).forEach(solicitacoes::add));

        Quando("for enviada a solicitação de criação de nova conta", () -> {
            String json = new ObjectMapper().writeValueAsString(solicitacoes.get(0));
            resultado = mockMvc.perform(post(URL_DEFAULT_CONTAS_BANCARIAS + "/criar")
                                                    .contentType(APPLICATION_JSON)
                                                    .content(json));
        });

        Então("deverá ser apresentada a seguinte mensagem de erro {string}", (String mensagem) -> {
            resultado.andExpect(status().is4xxClientError());
            resultado.andExpect(jsonPath(".message").value(mensagem));
        });

        Então("deverá ser retornado o número da conta criada", () ->
            resultado.andExpect(jsonPath(".numeroConta").isNotEmpty()));

        E("deverá ser apresentada a seguinte mensagem {string}", (String mensagem) ->
            resultado.andExpect(jsonPath(".message").value(mensagem)));

        Dado("que existam as seguintes contas", (DataTable dataTable) ->
                dataTable.asMaps().stream()
                        .map(ContaBancaria::new)
                        .forEach(contaBancaria -> contas.add(contaBancariaRepository.save(contaBancaria))));

        E("que seja solicitado um depósito de {string}", (String valorSaldo) ->
                this.saldoADepositar = valorSaldo);

        Quando("for executada a operação de depósito", () ->
                resultado = mockMvc.perform(put(URL_DEFAULT_CONTAS_BANCARIAS + "/depositar/{id}",
                                                contas.get(0).getId())
                                            .contentType(APPLICATION_JSON)
                                            .content(saldoADepositar)));

        E("o saldo da conta {string} deverá ser de {string}", (String numeroConta, String saldoDaConta) -> {
            Optional<ContaBancaria> contaBancaria = contaBancariaRepository.findByNumeroConta(numeroConta);
            assertThat(contaBancaria).isPresent();
            assertThat(contaBancaria.get().getSaldo()).isEqualTo(Double.parseDouble(saldoDaConta));
        });

        Dado("que seja solicitado um saque de {string}", (String valorDoSaque) ->
                this.valorSaque = valorDoSaque);

        Quando("for executada a operação de saque", () -> {
            resultado = mockMvc.perform(put(URL_DEFAULT_CONTAS_BANCARIAS + "/saque/{id}", contas.get(0).getId())
                                        .contentType(APPLICATION_JSON).content(valorSaque));
        });
    }
}
