package com.iftm.client.Services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.iftm.client.dto.ClientDTO;
import com.iftm.client.services.ClientService;
import com.iftm.client.services.exceptions.ResourceNotFoundException;

import java.time.Instant;

@SpringBootTest
public class ClientServiceTests {

    @Autowired
    private ClientService service;

    // Teste para verificar se findById lança ResourceNotFoundException quando o ID não existe
    @Test
    public void findById_ShouldThrowResourceNotFoundException_WhenIdDoesNotExist() {
        Long invalidId = 999L;
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(invalidId);
        });
    }

    // Teste para verificar se delete lança ResourceNotFoundException quando o ID não existe
    @Test
    @Transactional
    public void delete_ShouldThrowResourceNotFoundException_WhenIdDoesNotExist() {
        Long invalidId = 999L;
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(invalidId);
        });
    }
    
    // Teste para verificar se insert persiste os dados e retorna um DTO com ID não nulo e nome correto
    @Test
    @Transactional
    public void insert_ShouldPersistDataAndReturnDTO() {
        ClientDTO dto = new ClientDTO(null, "New Client", "09876543211", 3000.0, Instant.parse("1980-01-01T00:00:00Z"), 1);
        ClientDTO result = service.insert(dto);
        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("New Client", result.getName());
    }

    // Teste para verificar se update lança ResourceNotFoundException quando o ID não existe
    @Test
    @Transactional
    public void update_ShouldThrowResourceNotFoundException_WhenIdDoesNotExist() {
        ClientDTO dto = new ClientDTO(null, "Updated Client", "12345678900", 5000.0, Instant.parse("1990-01-01T00:00:00Z"), 2);
        Long invalidId = 999L;
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(invalidId, dto);
        });
    }
}