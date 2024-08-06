package com.iftm.client.dto;

import org.junit.jupiter.api.Test;

import com.iftm.client.entities.Client;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;

public class ClientDTOTests {

    @Test
    public void testToEntity_ShouldConvertDTOToEntity() {
        ClientDTO dto = new ClientDTO(1L, "Test Client", "12345678900", 4000.0, Instant.parse("1990-01-01T00:00:00Z"), 2);
        Client entity = dto.toEntity();

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getCpf(), entity.getCpf());
        assertEquals(dto.getIncome(), entity.getIncome());
        assertEquals(dto.getBirthDate(), entity.getBirthDate());
        assertEquals(dto.getChildren(), entity.getChildren());
    }
}
