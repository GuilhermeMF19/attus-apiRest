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
import com.gmf.attusapiRest.entities.Endereco;
import com.gmf.attusapiRest.entities.Pessoa;
import com.gmf.attusapiRest.services.EnderecoService;
import com.gmf.attusapiRest.services.PessoaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EnderecoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnderecoService enderecoService;

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
        when(enderecoService.findAll(any())).thenReturn(new PageImpl<>(Collections.emptyList()));
        mockMvc.perform(get("/api/endereco/v1"))
                .andExpect(status().isOk());
        verify(enderecoService, times(1)).findAll(any());
    }

    @Test
    void testFindById() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        when(enderecoService.findById(1L)).thenReturn(endereco);
        mockMvc.perform(get("/api/endereco/v1/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
        verify(enderecoService, times(1)).findById(1L);
    }

    @Test
    void testCreate() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        when(pessoaService.findById(any())).thenReturn(new Pessoa());
        when(enderecoService.create(any())).thenReturn(endereco);

        mockMvc.perform(post("/api/endereco/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(endereco)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
        verify(pessoaService, times(1)).findById(any());
        verify(enderecoService, times(1)).create(any());
    }

    @Test
    void testUpdate() throws Exception {
        Endereco endereco = new Endereco();
        endereco.setId(1L);
        when(enderecoService.update(any(), any())).thenReturn(endereco);

        mockMvc.perform(put("/api/endereco/v1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(endereco)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        verify(enderecoService, times(1)).update(any(), any());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/endereco/v1/1"))
                .andExpect(status().isNoContent());
        verify(enderecoService, times(1)).deleteById(1L);
    }
}
