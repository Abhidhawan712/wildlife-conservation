package com.wildlife.conservation.service;

import com.wildlife.conservation.dto.ObservationRequest;
import com.wildlife.conservation.dto.ReportResponse;
import com.wildlife.conservation.entity.Observation;
import com.wildlife.conservation.exception.ResourceNotFoundException;
import com.wildlife.conservation.repository.ObservationRepository;
import com.wildlife.conservation.repository.RangerRepository;
import com.wildlife.conservation.repository.SpeciesRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ObservationService {

    private final ObservationRepository observationRepository;
    private final RangerRepository rangerRepository;
    private final SpeciesRepository speciesRepository;

    public ObservationService(ObservationRepository observationRepository,
                               RangerRepository rangerRepository,
                               SpeciesRepository speciesRepository) {
        this.observationRepository = observationRepository;
        this.rangerRepository = rangerRepository;
        this.speciesRepository = speciesRepository;
    }

    public List<Observation> getAllObservations() {
        return observationRepository.findAll();
    }

    public Observation getObservationById(Long id) {
        return observationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Observation not found with id: " + id));
    }

    public Observation createObservation(ObservationRequest request) {
        Observation obs = new Observation();
        obs.setObservationTime(request.getObservationTime());
        obs.setLocation(request.getLocation());
        obs.setType(request.getType());
        obs.setNotes(request.getNotes());
        obs.setCount(request.getCount());

        obs.setRanger(rangerRepository.findById(request.getRangerId())
            .orElseThrow(() -> new ResourceNotFoundException("Ranger not found: " + request.getRangerId())));

        obs.setSpecies(speciesRepository.findById(request.getSpeciesId())
            .orElseThrow(() -> new ResourceNotFoundException("Species not found: " + request.getSpeciesId())));

        return observationRepository.save(obs);
    }

    public Observation updateObservation(Long id, ObservationRequest request) {
        Observation obs = getObservationById(id);
        obs.setObservationTime(request.getObservationTime());
        obs.setLocation(request.getLocation());
        obs.setType(request.getType());
        obs.setNotes(request.getNotes());
        obs.setCount(request.getCount());

        obs.setRanger(rangerRepository.findById(request.getRangerId())
            .orElseThrow(() -> new ResourceNotFoundException("Ranger not found: " + request.getRangerId())));

        obs.setSpecies(speciesRepository.findById(request.getSpeciesId())
            .orElseThrow(() -> new ResourceNotFoundException("Species not found: " + request.getSpeciesId())));

        return observationRepository.save(obs);
    }

    public void deleteObservation(Long id) {
        observationRepository.delete(getObservationById(id));
    }

    // Domain-specific: summary report
    public ReportResponse generateReport(LocalDateTime start, LocalDateTime end) {
        List<Object[]> counts = observationRepository.countObservationsPerSpeciesBetween(start, end);
        Map<String, Long> perSpecies = new LinkedHashMap<>();
        for (Object[] row : counts) {
            perSpecies.put((String) row[0], (Long) row[1]);
        }

        List<Observation> endangeredObs = observationRepository.findEndangeredSightings(start, end);
        List<String> alerts = endangeredObs.stream()
            .map(o -> "ALERT: Endangered species '" + o.getSpecies().getName() +
                      "' sighted at " + o.getLocation() + " on " + o.getObservationTime())
            .distinct()
            .toList();

        long total = observationRepository.findByObservationTimeBetween(start, end).size();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return new ReportResponse(start.format(fmt), end.format(fmt), perSpecies, alerts, total);
    }
}