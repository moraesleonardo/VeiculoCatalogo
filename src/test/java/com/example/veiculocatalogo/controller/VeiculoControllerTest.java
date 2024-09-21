package com.example.veiculocatalogo.controller;

import com.example.veiculocatalogo.model.Veiculo;
import com.example.veiculocatalogo.service.VeiculoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VeiculoService veiculoService;  // Mock do VeiculoService

    private Veiculo veiculoExistente;

    @BeforeEach
    public void setup() {
        veiculoExistente = new Veiculo(1, "Ford", "Fiesta", 2022);
        // Simula o comportamento do VeiculoService
        given(veiculoService.findById(1)).willReturn(Optional.of(veiculoExistente));
        given(veiculoService.save(any(Veiculo.class))).willReturn(veiculoExistente);
    }

    // Teste para verificar se a listagem de veículos funciona
    @Test
    public void deveListarTodosOsVeiculos() throws Exception {
        mockMvc.perform(get("/api/veiculos"))
                .andExpect(status().isOk()) // Verifica se o status é 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)); // Verifica o tipo de conteúdo retornado
    }

    // Teste para adicionar um veículo
    @Test
    public void deveAdicionarVeiculo() throws Exception {
        Veiculo veiculo = new Veiculo(1, "Ford", "Fiesta", 2022);

        mockMvc.perform(post("/api/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(veiculo)))
                .andExpect(status().isOk()); // Verifica se a adição foi bem-sucedida
    }


    // Teste para atualizar um veículo
    @Test
    public void deveAtualizarVeiculo() throws Exception {
        Veiculo veiculoAtualizado = new Veiculo(1, "Ford", "Focus", 2023);

        mockMvc.perform(put("/api/veiculos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(veiculoAtualizado)))
                .andExpect(status().isOk()); // Verifica se a atualização foi bem-sucedida
    }

    // Teste para deletar um veículo
    @Test
    public void deveRemoverVeiculo() throws Exception {
        mockMvc.perform(delete("/api/veiculos/1"))
                .andExpect(status().isOk()); // Verifica se a remoção foi bem-sucedida
    }
}
