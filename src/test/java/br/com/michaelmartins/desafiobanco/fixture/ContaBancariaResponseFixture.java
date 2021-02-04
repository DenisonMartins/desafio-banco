package br.com.michaelmartins.desafiobanco.fixture;

import br.com.michaelmartins.desafiobanco.dto.ContaBancariaDTO;

public class ContaBancariaResponseFixture {

    public static ContaBancariaDTO contaBancariaResponse() {
        return new ContaBancariaDTO(1L, "123", "1500", "Sucesso");
    }
}
