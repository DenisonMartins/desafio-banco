# desafio-banco
Esse projeto consiste em um conjunto de features para serem implementadas para simular um banco digital.

### O desafio
O desafio consiste em implementar os requisitos funcionais ou features de um banco digital fictício que contemple as operações de criação de novas contas, depósitos, saques e transferências entre contas.

- **Implementar uma API Rest baseada em Spring Boot** utilizando Java 8 ou superior seguindo as melhores práticas de qualidade de código.
- Implementar um **conjunto de testes automáticos, que interaja com a API** criada no item 1, **utilizando o framework Cucumber** e as metodologias de desenvolvimento guiado por teste **TDD e BDD**.

---

Seguem os requisitos funcionais ou features:

* [Criar Conta](src/test/resources/br/com/michaelmartins/desafiobanco/bdd/criar_conta.feature) - Feature de criação de uma nova conta no banco
* [Depósito](src/test/resources/br/com/michaelmartins/desafiobanco/bdd/deposito.feature) - Feature de realização da operação de depósito em conta.
* [Saque](src/test/resources/br/com/michaelmartins/desafiobanco/bdd/saque.feature) - Feature de realização da operação de saque em conta.
* [Transferência](src/test/resources/br/com/michaelmartins/desafiobanco/bdd/transferencia.feature) - Feature de realização da operação transferência de valores entre contas.

---

# Tecnologias utilizadas
## Backend
- Java 11
- Spring Boot
- Cucumber
- H2