package com.gmf.attusapiRest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.gmf.attusapiRest.entities.Pessoa;
import com.gmf.attusapiRest.services.PessoaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(value = "/api/pessoa/v1")
@Tag(name = "Pessoa", description = "Endpoints para gerenciamento de pessoas.")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping
	@Operation(summary = "Todos os pessoas.", description = "Todos os pessoas.", 
	tags = {"Pessoa"}, 
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = Pessoa.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public ResponseEntity<Page<Pessoa>> findAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findAll(pageable));
	}
	
	@GetMapping(value="/{id}") 
	@Operation(summary = "Uma pessoa.", description = "Uma pessoa.", 
	tags = {"Pessoa"}, 
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Pessoa.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public ResponseEntity<Pessoa> findById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findById(id));
	}
	
	@PostMapping
	@Operation(summary = "Adiciona uma pessoa.", 
	description = "Adiciona uma pessoa.", 
	tags = {"Pessoa"}, 
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Pessoa.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public ResponseEntity<Pessoa> create(@RequestBody Pessoa pessoa) {
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.create(pessoa));
	}
	
	@PutMapping(value="/{id}")
	@Operation(summary = "Atualiza uma pessoa.", 
	description = "Atualiza uma pessoa.", 
	tags = {"Pessoa"}, 
	responses = {
			@ApiResponse(description = "Updated", responseCode = "200", 
					content = @Content(schema = @Schema(implementation = Pessoa.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public ResponseEntity<Pessoa> update(@PathVariable Long id, @RequestBody Pessoa pessoa) {
		return ResponseEntity.status(HttpStatus.OK).body(pessoaService.update(id, pessoa));
	}
	
	@DeleteMapping(value="/{id}")
	@Operation(summary = "Deleta uma pessoa.", 
	description = "Deleta uma pessoa.", 
	tags = {"Pessoa"}, 
	responses = {
			@ApiResponse(description = "No content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthourized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
	        pessoaService.deleteById(id);
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	    } catch (EmptyResultDataAccessException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há uma pessoa com esse ID!");
	    }
	}
}
