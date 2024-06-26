package com.gmf.attusapiRest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmf.attusapiRest.entities.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
}
