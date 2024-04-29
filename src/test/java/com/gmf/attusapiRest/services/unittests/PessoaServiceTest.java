package com.gmf.attusapiRest.services.unittests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gmf.attusapiRest.entities.Pessoa;
import com.gmf.attusapiRest.repositories.PessoaRepository;
import com.gmf.attusapiRest.services.PessoaService;
import com.gmf.attusapiRest.services.exceptions.NotFoundException;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {


    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Test
    void testFindAll() {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Pessoa(1L, "Fulano", LocalDate.of(1990, 5, 15)));
        pessoas.add(new Pessoa(2L, "Ciclano", LocalDate.of(1985, 3, 10)));

        when(pessoaRepository.findAll((Pageable) any())).thenReturn(new PageImpl<>(pessoas));

        Page<Pessoa> result = pessoaService.findAll(Pageable.unpaged());

        assertEquals(2, result.getContent().size());
    }

    @Test
    void testFindById() {
        Long id = 1L;
        Pessoa pessoa = new Pessoa(id, "Fulano", LocalDate.of(1990, 5, 15));

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));

        Pessoa result = pessoaService.findById(id);

        assertEquals(pessoa, result);
    }
    
    @Test
    void testFindByIdNotFound() {
        Long pessoaId = 1L;
        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pessoaService.findById(pessoaId));
    }

    @Test
    void testCreate() {
        Pessoa pessoa = new Pessoa(null, "Fulano", LocalDate.of(1990, 5, 15));
        Pessoa pessoaWithId = new Pessoa(1L, "Fulano", LocalDate.of(1990, 5, 15));

        when(pessoaRepository.save(pessoa)).thenReturn(pessoaWithId);

        Pessoa result = pessoaService.create(pessoa);

        assertNotNull(result.getId());
        assertEquals(pessoa.getNome(), result.getNome());
        assertEquals(pessoa.getDataNascimento(), result.getDataNascimento());
    }
    
    @Test
    void testCreateWithNullPessoa() {
        when(pessoaRepository.save(null)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DataIntegrityViolationException.class, () -> pessoaService.create(null));
    }

    @Test
    void testDeleteById() {
        Long id = 1L;

        assertDoesNotThrow(() -> pessoaService.deleteById(id));
    }
    
    @Test
    void testDeleteByIdNotFound() {
        Long pessoaId = 1L;
        doThrow(EmptyResultDataAccessException.class).when(pessoaRepository).deleteById(pessoaId);

        assertThrows(NotFoundException.class, () -> pessoaService.deleteById(pessoaId));
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        Pessoa existingPessoa = new Pessoa(id, "Fulano", LocalDate.of(1990, 5, 15));
        Pessoa updatedPessoa = new Pessoa(null, "Ciclano", LocalDate.of(1985, 3, 10));

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(existingPessoa));
        when(pessoaRepository.save(existingPessoa)).thenReturn(existingPessoa);

        Pessoa result = pessoaService.update(id, updatedPessoa);

        assertEquals(id, result.getId());
        assertEquals(updatedPessoa.getNome(), result.getNome());
        assertEquals(updatedPessoa.getDataNascimento(), result.getDataNascimento());
    }
    
    @Test
    void testUpdateNotFound() {
        Long pessoaId = 1L;
        Pessoa updatedPessoa = new Pessoa(pessoaId, "Fulano", LocalDate.of(1990, 5, 15));
        when(pessoaRepository.findById(pessoaId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pessoaService.update(pessoaId, updatedPessoa));
    }
}
