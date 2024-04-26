package com.gmf.attusapiRest.dtos;

public class EnderecoDto {

	private Long id;
    private String logradouro;
    private String cep;
    private String numero;
    private String cidade;
    private String estado;
    private Boolean principal = Boolean.FALSE;
    private Long pessoa_id;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Boolean getPrincipal() {
		return principal;
	}
	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}
	public Long getPessoa_id() {
		return pessoa_id;
	}
	public void setPessoa_id(Long pessoa_id) {
		this.pessoa_id = pessoa_id;
	}
}
