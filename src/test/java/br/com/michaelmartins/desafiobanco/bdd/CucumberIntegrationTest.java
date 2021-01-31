package br.com.michaelmartins.desafiobanco.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/br/com/michaelmartins/desafiobanco/bdd")
public class CucumberIntegrationTest {
}
