package br.com.michaelmartins.desafiobanco.fixture;

import br.com.michaelmartins.desafiobanco.dto.ContaBancariaResponse;

public class ContaBancariaResponseFixture {

    public static ContaBancariaResponse contaBancariaResponse() {
        return new ContaBancariaResponse(1L, "123", "1500", "Sucesso");
    }
}
