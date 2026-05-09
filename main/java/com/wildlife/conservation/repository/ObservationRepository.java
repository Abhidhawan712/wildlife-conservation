package com.wildlife.conservation.repository;

import com.wildlife.conservation.entity.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface ObservationRepository extends JpaRepository<Observation, Long> {

    List<Observation> findByRangerId(Long rangerId);

    List<Observation> findBySpeciesId(Long speciesId);

    // Custom query: count observations per species within a date range
    @Query("SELECT o.species.name, COUNT(o) FROM Observation o " +
           "WHERE o.observationTime BETWEEN :start AND :end " +
           "GROUP BY o.species.name")
    List<Object[]> countObservationsPerSpeciesBetween(
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );

    // Alert: sightings of endangered species
    @Query("SELECT o FROM Observation o JOIN o.species s " +
           "WHERE s.endangered = true AND o.observationTime BETWEEN :start AND :end")
    List<Observation> findEndangeredSightings(
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );

    List<Observation> findByObservationTimeBetween(LocalDateTime start, LocalDateTime end);
}