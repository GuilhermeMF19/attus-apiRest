package com.gmf.attusapiRest.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.gmf.attusapiRest.entities.Pessoa;
import com.gmf.attusapiRest.dtos.EnderecoDto;
import com.gmf.attusapiRest.entities.Endereco;
import com.gmf.attusapiRest.services.EnderecoService;
import com.gmf.attusapiRest.services.PessoaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(value = "/api/endereco/v1")
@Tag(name = "Endereço", description = "Endpoints para gerenciamento de endereços.")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping
	@Operation(summary = "Todos os endereços.", description = "Todos os endereços.", 
	tags = {"Endereço"}, 
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = Endereco.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public ResponseEntity<Page<Endereco>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(enderecoService.findAll(pageable));
	}
	
	@GetMapping(value="/{id}")
	@Operation(summary = "Um endereço.", description = "Um endereço.", 
	tags = {"Endereço"}, 
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Endereco.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public ResponseEntity<Endereco> findById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(enderecoService.findById(id));
	}
	
	@PostMapping
	@Operation(summary = "Adiciona um endereço.", 
	description = "Adiciona um endereço.", 
	tags = {"Endereço"}, 
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Endereco.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public ResponseEntity<Object> create(@RequestBody EnderecoDto enderecoDto) {
		Pessoa pessoa = pessoaService.findById(enderecoDto.getPessoa_id());
		if (pessoa == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pessoa com o ID fornecido não encontrada.");
		}

		var endereco = new Endereco();
		BeanUtils.copyProperties(enderecoDto, endereco, "pessoa_id");
		endereco.setPessoa(pessoa);
		return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.create(endereco));
	}
	
	@PutMapping(value="/{id}")
	@Operation(summary = "Atualiza um endereço.", 
	description = "Atualiza um endereço.", 
	tags = {"Endereço"}, 
	responses = {
			@ApiResponse(description = "Updated", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Endereco.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public ResponseEntity<Endereco> update(@PathVariable Long id, @RequestBody Endereco endereco) {
		return ResponseEntity.status(HttpStatus.OK).body(enderecoService.update(id, endereco));
	}
	
	@DeleteMapping(value="/{id}")
	@Operation(summary = "Deleta um endereço.", 
	description = "Deleta um endereço.", 
	tags = {"Endereço"}, 
	responses = {
			@ApiResponse(description = "No content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
	        enderecoService.deleteById(id);
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	    } catch (EmptyResultDataAccessException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há uma endereco com esse ID!");
	    }
	}
}
