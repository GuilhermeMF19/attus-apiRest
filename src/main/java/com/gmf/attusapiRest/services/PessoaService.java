package com.gmf.attusapiRest.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmf.attusapiRest.entities.Pessoa;
import com.gmf.attusapiRest.repositories.PessoaRepository;
import com.gmf.attusapiRest.services.exceptions.NotFoundException;

import jakarta.transaction.Transactional;


@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Page<Pessoa> findAll(Pageable pageable) {
		return pessoaRepository.findAll(pageable);
	}
	
	public Pessoa findById(Long id) {
		Optional<Pessoa> obj = pessoaRepository.findById(id);
		return obj.orElseThrow(() -> new NotFoundException("Não há uma pessoa com esse ID!"));
	}
	
	@Transactional
	public Pessoa create(Pessoa obj) {
		return pessoaRepository.save(obj);
	}
	
	@Transactional
	public void deleteById(Long id) {
		try {
			pessoaRepository.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundException("Não há uma pessoa com esse ID!");
		}
	}
	
	@Transactional
	public Pessoa update(Long id, Pessoa obj) {
		Optional<Pessoa> optionalEntity = pessoaRepository.findById(id);
		Pessoa entity = optionalEntity.orElseThrow(() -> new NotFoundException("Não há uma pessoa com esse ID!"));
		
		updateData(entity, obj);
		return pessoaRepository.save(entity);			
	}

	private void updateData(Pessoa entity, Pessoa obj) {
		entity.setNome(obj.getNome());
		entity.setDataNascimento(obj.getDataNascimento());
		entity.getEnderecos().clear();
		entity.getEnderecos().addAll(obj.getEnderecos());
	}
}
