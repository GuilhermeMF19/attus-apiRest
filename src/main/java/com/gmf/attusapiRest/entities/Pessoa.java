package com.gmf.attusapiRest.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_PESSOA")
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private LocalDate dataNascimento;
	
	@OneToMany(mappedBy = "pessoa", orphanRemoval=true)
    @JsonIgnoreProperties(value = "pessoa")
	private List<Endereco> enderecos = new ArrayList<>();

	public Pessoa() {}

	public Pessoa(Long id, String nome, LocalDate dataNascimento) {
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	
	public Endereco getEnderecoPrincipal(List<Endereco> list) {
		for (Endereco endereco : list) {
			if(endereco.isPrincipal()) {
				return endereco;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataNascimento, id, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		return Objects.equals(dataNascimento, other.dataNascimento) && Objects.equals(id, other.id)
				&& Objects.equals(nome, other.nome);
	}
}
