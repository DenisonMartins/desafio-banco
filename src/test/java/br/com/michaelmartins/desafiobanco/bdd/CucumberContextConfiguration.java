package br.com.michaelmartins.desafiobanco.bdd;

import io.cucumber.java8.Pt;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
@ContextConfiguration(classes = { CucumberIntegrationTest.class })
public class CucumberContextConfiguration implements Pt {
}
