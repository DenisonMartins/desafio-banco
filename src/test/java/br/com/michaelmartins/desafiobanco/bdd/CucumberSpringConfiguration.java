package br.com.michaelmartins.desafiobanco.bdd;

import io.cucumber.java8.Pt;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@CucumberContextConfiguration
@AutoConfigureMockMvc
public class CucumberSpringConfiguration implements Pt {
}
