package com.gmf.attusapiRest.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmf.attusapiRest.entities.Endereco;
import com.gmf.attusapiRest.repositories.EnderecoRepository;
import com.gmf.attusapiRest.services.exceptions.NotFoundException;

import jakarta.transaction.Transactional;


@Service
public class EnderecoService {
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Page<Endereco> findAll(Pageable pageable) {
		return enderecoRepository.findAll(pageable);
	}
	
	public Endereco findById(Long id) {
		Optional<Endereco> obj = enderecoRepository.findById(id);
		return obj.orElseThrow(() -> new NotFoundException("Não há uma endereco com esse ID!"));
	}
	
	@Transactional
	public Endereco create(Endereco obj) {
		return enderecoRepository.save(obj);
	}
	
	@Transactional
	public void deleteById(Long id) {
		try {
			enderecoRepository.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			throw new NotFoundException("Não há uma endereco com esse ID!");
		}
	}
	
	@Transactional
	public Endereco update(Long id, Endereco obj) {
		Optional<Endereco> optionalEntity = enderecoRepository.findById(id);
		Endereco entity = optionalEntity.orElseThrow(() -> new NotFoundException("Não há uma endereco com esse ID!"));
		
		updateData(entity, obj);
		return enderecoRepository.save(entity);			
	}

	private void updateData(Endereco entity, Endereco obj) {
		entity.setLogradouro(obj.getLogradouro());
		entity.setCep(obj.getCep());
		entity.setNumero(obj.getNumero());
		entity.setCidade(obj.getCidade());
		entity.setEstado(obj.getEstado());
	}
}
