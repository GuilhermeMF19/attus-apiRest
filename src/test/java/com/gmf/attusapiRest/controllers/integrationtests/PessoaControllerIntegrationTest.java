package com.gmf.attusapiRest.controllers.integrationtests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmf.attusapiRest.entities.Pessoa;
import com.gmf.attusapiRest.services.PessoaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PessoaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaService pessoaService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() throws Exception {
        when(pessoaService.findAll(any())).thenReturn(new PageImpl<>(Collections.emptyList()));
        mockMvc.perform(get("/api/pessoa/v1"))
                .andExpect(status().isOk());
        verify(pessoaService, times(1)).findAll(any());
    }

    @Test
    void testFindById() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("João");
        pessoa.setDataNascimento(LocalDate.of(1990, 1, 1));
        when(pessoaService.findById(1L)).thenReturn(pessoa);
        mockMvc.perform(get("/api/pessoa/v1/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.dataNascimento").value("1990-01-01"));
        verify(pessoaService, times(1)).findById(1L);
    }

    @Test
    void testCreate() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("João");
        pessoa.setDataNascimento(LocalDate.of(1990, 1, 1));
        when(pessoaService.create(any())).thenReturn(pessoa);

        mockMvc.perform(post("/api/pessoa/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoa)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.dataNascimento").value("1990-01-01"));
        verify(pessoaService, times(1)).create(any());
    }

    @Test
    void testUpdate() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("João");
        pessoa.setDataNascimento(LocalDate.of(1990, 1, 1));
        when(pessoaService.update(any(), any())).thenReturn(pessoa);

        mockMvc.perform(put("/api/pessoa/v1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoa)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.dataNascimento").value("1990-01-01"));
        verify(pessoaService, times(1)).update(any(), any());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/pessoa/v1/1"))
                .andExpect(status().isNoContent());
        verify(pessoaService, times(1)).deleteById(1L);
    }
}