package com.wildlife.conservation.service;

import com.wildlife.conservation.dto.ObservationRequest;
import com.wildlife.conservation.dto.ReportResponse;
import com.wildlife.conservation.entity.Observation;
import com.wildlife.conservation.entity.Ranger;
import com.wildlife.conservation.entity.Species;
import com.wildlife.conservation.exception.ResourceNotFoundException;
import com.wildlife.conservation.repository.ObservationRepository;
import com.wildlife.conservation.repository.RangerRepository;
import com.wildlife.conservation.repository.SpeciesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObservationServiceTest {

    @Mock
    private ObservationRepository observationRepository;

    @Mock
    private RangerRepository rangerRepository;

    @Mock
    private SpeciesRepository speciesRepository;

    @InjectMocks
    private ObservationService observationService;

    private Ranger ranger;
    private Species species;
    private ObservationRequest request;

    @BeforeEach
    void setUp() {
        ranger = new Ranger();
        ranger.setId(1L);
        ranger.setName("Ranger One");

        species = new Species();
        species.setId(1L);
        species.setName("Bengal Tiger");
        species.setEndangered(true);

        request = new ObservationRequest();
        request.setObservationTime(LocalDateTime.now());
        request.setLocation("Zone A");
        request.setType("SIGHTING");
        request.setNotes("Healthy specimen");
        request.setCount(1);
        request.setRangerId(1L);
        request.setSpeciesId(1L);
    }

    @Test
    void testCreateObservation_Success() {
        when(rangerRepository.findById(1L)).thenReturn(Optional.of(ranger));
        when(speciesRepository.findById(1L)).thenReturn(Optional.of(species));
        when(observationRepository.save(any(Observation.class))).thenAnswer(i -> {
            Observation o = (Observation) i.getArgument(0);
            o.setId(1L);
            return o;
        });

        Observation result = observationService.createObservation(request);

        assertNotNull(result);
        assertEquals("Zone A", result.getLocation());
        assertEquals("SIGHTING", result.getType());
        assertEquals(ranger, result.getRanger());
        assertEquals(species, result.getSpecies());
    }

    @Test
    void testCreateObservation_RangerNotFound() {
        when(rangerRepository.findById(99L)).thenReturn(Optional.empty());
        request.setRangerId(99L);
        assertThrows(ResourceNotFoundException.class,
                () -> observationService.createObservation(request));
    }

    @Test
    void testGenerateReport_ReturnsCorrectStructure() {
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();

        // FIX: properly typed List<Object[]>
        List<Object[]> countResult = new ArrayList<>();
        countResult.add(new Object[]{"Bengal Tiger", 3L});

        when(observationRepository.countObservationsPerSpeciesBetween(start, end))
                .thenReturn(countResult);
        when(observationRepository.findEndangeredSightings(start, end))
                .thenReturn(List.of());
        when(observationRepository.findByObservationTimeBetween(start, end))
                .thenReturn(List.of(new Observation()));

        ReportResponse report = observationService.generateReport(start, end);

        assertNotNull(report);
        assertEquals(1L, report.getTotalObservations());
        assertTrue(report.getObservationsPerSpecies().containsKey("Bengal Tiger"));
        assertEquals(3L, report.getObservationsPerSpecies().get("Bengal Tiger"));
    }

    @Test
    void testDeleteObservation_ThrowsIfNotFound() {
        when(observationRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> observationService.deleteObservation(99L));
    }
}