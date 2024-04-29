package com.gmf.attusapiRest.services.unittests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.gmf.attusapiRest.entities.Endereco;
import com.gmf.attusapiRest.repositories.EnderecoRepository;
import com.gmf.attusapiRest.services.EnderecoService;
import com.gmf.attusapiRest.services.exceptions.NotFoundException;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    @Test
    void testFindAll() {
        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(new Endereco("Rua A", "12345-678", "100", "Cidade A", "Estado A", true));
        enderecos.add(new Endereco("Rua B", "54321-876", "200", "Cidade B", "Estado B", false));
        Page<Endereco> page = new PageImpl<>(enderecos);
        when(enderecoRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Endereco> result = enderecoService.findAll(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void testFindById() {
        Long enderecoId = 1L;
        Endereco endereco = new Endereco("Rua A", "12345-678", "100", "Cidade A", "Estado A", true);
        endereco.setId(enderecoId);
        Optional<Endereco> optionalEndereco = Optional.of(endereco);
        when(enderecoRepository.findById(enderecoId)).thenReturn(optionalEndereco);

        Endereco result = enderecoService.findById(enderecoId);

        assertNotNull(result);
        assertEquals(enderecoId, result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Long enderecoId = 1L;
        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> enderecoService.findById(enderecoId));
    }

    @Test
    void testCreate() {
        Endereco endereco = new Endereco("Rua A", "12345-678", "100", "Cidade A", "Estado A", true);
        endereco.setId(1L); 
        when(enderecoRepository.save(endereco)).thenReturn(endereco);

        Endereco result = enderecoService.create(endereco);

        assertNotNull(result.getId());
        assertEquals(endereco.getLogradouro(), result.getLogradouro());
        assertEquals(endereco.getCep(), result.getCep());
        assertEquals(endereco.getNumero(), result.getNumero());
        assertEquals(endereco.getCidade(), result.getCidade());
        assertEquals(endereco.getEstado(), result.getEstado());
        assertEquals(endereco.isPrincipal(), result.isPrincipal());
    }
    
    @Test
    void testCreateWithNullEndereco() {
        when(enderecoRepository.save(null)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DataIntegrityViolationException.class, () -> enderecoService.create(null));
    }

    @Test
    void testDeleteById() {
        Long enderecoId = 1L;

        assertDoesNotThrow(() -> enderecoService.deleteById(enderecoId));
        verify(enderecoRepository, times(1)).deleteById(enderecoId);
    }

    @Test
    void testDeleteByIdNotFound() {
        Long enderecoId = 1L;
        doThrow(EmptyResultDataAccessException.class).when(enderecoRepository).deleteById(enderecoId);

        assertThrows(NotFoundException.class, () -> enderecoService.deleteById(enderecoId));
    }

    @Test
    void testUpdate() {
        Long enderecoId = 1L;
        Endereco endereco = new Endereco("Rua A", "12345-678", "100", "Cidade A", "Estado A", true);
        endereco.setId(enderecoId);
        Endereco updatedEndereco = new Endereco("Rua B", "54321-876", "200", "Cidade B", "Estado B", false);
        updatedEndereco.setId(enderecoId);
        Optional<Endereco> optionalEndereco = Optional.of(endereco);
        
        when(enderecoRepository.findById(enderecoId)).thenReturn(optionalEndereco);
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(updatedEndereco);
        
        Endereco result = enderecoService.update(enderecoId, updatedEndereco);

        assertNotNull(result);
        assertEquals(updatedEndereco.getLogradouro(), result.getLogradouro());
        assertEquals(updatedEndereco.getCep(), result.getCep());
        assertEquals(updatedEndereco.getNumero(), result.getNumero());
        assertEquals(updatedEndereco.getCidade(), result.getCidade());
        assertEquals(updatedEndereco.getEstado(), result.getEstado());
        assertEquals(updatedEndereco.isPrincipal(), result.isPrincipal());
    }


    @Test
    void testUpdateNotFound() {
        Long enderecoId = 1L;
        Endereco updatedEndereco = new Endereco("Rua B", "54321-876", "200", "Cidade B", "Estado B", false);
        when(enderecoRepository.findById(enderecoId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> enderecoService.update(enderecoId, updatedEndereco));
    }
}
