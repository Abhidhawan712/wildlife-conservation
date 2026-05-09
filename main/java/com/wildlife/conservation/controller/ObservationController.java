package com.wildlife.conservation.controller;

import com.wildlife.conservation.dto.ObservationRequest;
import com.wildlife.conservation.entity.Observation;
import com.wildlife.conservation.service.ObservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/observations")
@Tag(name = "Observations", description = "Record and manage wildlife observations")
public class ObservationController {

    private final ObservationService observationService;

    public ObservationController(ObservationService observationService) {
        this.observationService = observationService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('RANGER','ADMIN')")
    public ResponseEntity<List<Observation>> getAll() {
        return ResponseEntity.ok(observationService.getAllObservations());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('RANGER','ADMIN')")
    public ResponseEntity<Observation> getById(@PathVariable Long id) {
        return ResponseEntity.ok(observationService.getObservationById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('RANGER','ADMIN')")
    public ResponseEntity<Observation> create(@Valid @RequestBody ObservationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(observationService.createObservation(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('RANGER','ADMIN')")
    public ResponseEntity<Observation> update(@PathVariable Long id,
                                               @Valid @RequestBody ObservationRequest request) {
        return ResponseEntity.ok(observationService.updateObservation(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        observationService.deleteObservation(id);
        return ResponseEntity.noContent().build();
    }
}