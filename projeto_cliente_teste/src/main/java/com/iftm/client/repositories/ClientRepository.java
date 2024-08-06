package com.iftm.client.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.Instant;
import com.iftm.client.entities.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT obj FROM Client obj WHERE LOWER(obj.name) = LOWER(:name)")
    Client findByNameIgnoreCase(@Param("name") String name);

    @Query("SELECT obj FROM Client obj WHERE LOWER(obj.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Client> findByNameContainingIgnoreCase(@Param("name") String name);

    List<Client> findByIncomeGreaterThan(Double income);

    List<Client> findByIncomeLessThan(Double income);

    List<Client> findByIncomeBetween(Double minIncome, Double maxIncome);

    List<Client> findByBirthDateBetween(Instant startDate, Instant endDate);
}