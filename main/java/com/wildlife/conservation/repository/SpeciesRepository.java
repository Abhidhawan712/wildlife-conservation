package com.wildlife.conservation.repository;

import com.wildlife.conservation.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpeciesRepository extends JpaRepository<Species, Long> {
    List<Species> findByEndangeredTrue();
    boolean existsByName(String name);
}