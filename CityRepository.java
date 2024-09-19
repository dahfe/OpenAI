package com.gpt.spring_gpt_4o.repository;

import com.gpt.spring_gpt_4o.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {
    List<City> findByNameContainingIgnoreCase(String name);
    List<City> findByStateContainingIgnoreCase(String state);
    Optional<City> findByName(String name);
}