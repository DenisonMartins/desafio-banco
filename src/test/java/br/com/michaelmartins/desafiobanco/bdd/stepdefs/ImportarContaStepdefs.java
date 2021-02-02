package br.com.michaelmartins.desafiobanco.bdd.stepdefs;

import br.com.michaelmartins.desafiobanco.dto.SolicitacaoConta;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.Pt;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ImportarContaStepdefs implements Pt {

    private ResultActions resultadoSolicitacoes;

    public ImportarContaStepdefs(MockMvc mockMvc) {

        List<SolicitacaoConta> solicitacoes = new ArrayList<>();

        Dado("que seja solicitada a criação de uma nova conta com os seguintes valores", (DataTable dataTable) ->
                dataTable.asMaps().stream().map(SolicitacaoConta::new).forEach(solicitacoes::add));

        Quando("for enviada a solicitação de criação de nova conta", () -> {
            String json = new ObjectMapper().writeValueAsString(solicitacoes.get(0));
            resultadoSolicitacoes = mockMvc.perform(post("/contas-bancarias/criar")
                                                    .contentType(APPLICATION_JSON)
                                                    .content(json));
        });

        Então("deverá ser apresentada a seguinte mensagem de erro {string}", (String mensagem) -> {
            resultadoSolicitacoes.andExpect(status().is4xxClientError());
            resultadoSolicitacoes.andExpect(jsonPath(".message").value(mensagem));
        });

        Então("deverá ser retornado o número da conta criada", () -> {
            resultadoSolicitacoes.andExpect(jsonPath(".numeroConta").isNotEmpty());
        });

        E("deverá ser apresentada a seguinte mensagem {string}", (String mensagem) -> {
            resultadoSolicitacoes.andExpect(jsonPath(".message").value(mensagem));
        });
    }
}
