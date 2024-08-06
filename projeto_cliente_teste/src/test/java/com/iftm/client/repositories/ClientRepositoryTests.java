package com.iftm.client.repositories;

import com.iftm.client.entities.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class ClientRepositoryTests {

    @Autowired
    private ClientRepository repository;

    private Client client;

    @BeforeEach
    public void setUp() {
        client = new Client(null, "Test Client", "12345678900", 4000.0, Instant.parse("1990-01-01T00:00:00Z"), 2);
        repository.save(client);
    }

    @Test
    public void findByNameIgnoreCase_ShouldReturnClient_WhenNameExists() {
        // Teste para verificar se findByNameIgnoreCase retorna o cliente quando o nome existe (ignorando maiúsculas/minúsculas)
        Client result = repository.findByNameIgnoreCase("test client");
        Assertions.assertNotNull(result);
        Assertions.assertEquals(client.getId(), result.getId());
    }

    @Test
    public void findByNameIgnoreCase_ShouldReturnNull_WhenNameDoesNotExist() {
        // Teste para verificar se findByNameIgnoreCase retorna null quando o nome não existe
        Client result = repository.findByNameIgnoreCase("nonexistent");
        Assertions.assertNull(result);
    }

    @Test
    public void findByNameContainingIgnoreCase_ShouldReturnClients_WhenNamePartExists() {
        // Teste para verificar se findByNameContainingIgnoreCase retorna clientes quando parte do nome existe (ignorando maiúsculas/minúsculas)
        List<Client> result = repository.findByNameContainingIgnoreCase("test");
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void findByNameContainingIgnoreCase_ShouldReturnEmptyList_WhenNamePartDoesNotExist() {
        // Teste para verificar se findByNameContainingIgnoreCase retorna uma lista vazia quando parte do nome não existe
        List<Client> result = repository.findByNameContainingIgnoreCase("nonexistent");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findByNameContainingIgnoreCase_ShouldReturnAllClients_WhenNameIsEmpty() {
        // Teste para verificar se findByNameContainingIgnoreCase retorna todos os clientes quando o nome está vazio
        List<Client> result = repository.findByNameContainingIgnoreCase("");
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void findByIncomeGreaterThan_ShouldReturnClients_WhenIncomeIsGreaterThanValue() {
        // Teste para verificar se findByIncomeGreaterThan retorna clientes quando a renda é maior que o valor especificado
        List<Client> result = repository.findByIncomeGreaterThan(3000.0);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void findByIncomeLessThan_ShouldReturnClients_WhenIncomeIsLessThanValue() {
        // Teste para verificar se findByIncomeLessThan retorna clientes quando a renda é menor que o valor especificado
        List<Client> result = repository.findByIncomeLessThan(5000.0);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void findByIncomeBetween_ShouldReturnClients_WhenIncomeIsWithinRange() {
        // Teste para verificar se findByIncomeBetween retorna clientes quando a renda está dentro do intervalo especificado
        List<Client> result = repository.findByIncomeBetween(3000.0, 5000.0);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void findByBirthDateBetween_ShouldReturnClients_WhenBirthDateIsWithinRange() {
        // Teste para verificar se findByBirthDateBetween retorna clientes quando a data de nascimento está dentro do intervalo especificado
        Instant startDate = Instant.parse("1980-01-01T00:00:00Z");
        Instant endDate = Instant.now();
        List<Client> result = repository.findByBirthDateBetween(startDate, endDate);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void updateClient_ShouldUpdateClientDetails() {
        // Teste para verificar se updateClient atualiza os detalhes do cliente
        Optional<Client> optionalClient = repository.findById(client.getId());
        Assertions.assertTrue(optionalClient.isPresent());
        
        Client clientToUpdate = optionalClient.get();
        clientToUpdate.setName("Updated Client");
        clientToUpdate.setIncome(5000.0);
        clientToUpdate.setBirthDate(Instant.parse("1985-01-01T00:00:00Z"));
        repository.save(clientToUpdate);

        Client updatedClient = repository.findById(client.getId()).get();
        Assertions.assertEquals("Updated Client", updatedClient.getName());
        Assertions.assertEquals(5000.0, updatedClient.getIncome());
        Assertions.assertEquals(Instant.parse("1985-01-01T00:00:00Z"), updatedClient.getBirthDate());
    }

    @Test
    public void findByIncomeGreaterThan_ShouldReturnEmptyList_WhenNoClientMeetsCriteria() {
        // Teste para verificar se findByIncomeGreaterThan retorna uma lista vazia quando nenhum cliente atende aos critérios de renda
        List<Client> result = repository.findByIncomeGreaterThan(10000.0);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findByIncomeLessThan_ShouldReturnEmptyList_WhenNoClientMeetsCriteria() {
        // Teste para verificar se findByIncomeLessThan retorna uma lista vazia quando nenhum cliente atende aos critérios de renda
        List<Client> result = repository.findByIncomeLessThan(1000.0);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findByIncomeBetween_ShouldReturnEmptyList_WhenNoClientMeetsCriteria() {
        // Teste para verificar se findByIncomeBetween retorna uma lista vazia quando nenhum cliente atende aos critérios de renda
        List<Client> result = repository.findByIncomeBetween(1000.0, 2000.0);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findByBirthDateBetween_ShouldReturnEmptyList_WhenNoClientMeetsCriteria() {
        // Teste para verificar se findByBirthDateBetween retorna uma lista vazia quando nenhum cliente atende aos critérios de data de nascimento
        Instant startDate = Instant.parse("2000-01-01T00:00:00Z");
        Instant endDate = Instant.parse("2010-01-01T00:00:00Z");
        List<Client> result = repository.findByBirthDateBetween(startDate, endDate);
        Assertions.assertTrue(result.isEmpty());
    }
}