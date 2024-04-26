package com.gmf.attusapiRest.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmf.attusapiRest.entities.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	@EntityGraph(attributePaths = "enderecos")
    List<Pessoa> findAll();

    @EntityGraph(attributePaths = "enderecos")
    Optional<Pessoa> findById(Long id);
}
