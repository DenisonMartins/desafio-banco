package br.com.michaelmartins.desafiobanco.service.impl;

import br.com.michaelmartins.desafiobanco.service.GeradorNumeroConta;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class GeradorNumeroContaImpl implements GeradorNumeroConta {

    @Override
    public String gerar() {
        return RandomStringUtils.randomNumeric(5);
    }
}
