package br.com.michaelmartins.desafiobanco.repository;

import br.com.michaelmartins.desafiobanco.domain.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {
}
