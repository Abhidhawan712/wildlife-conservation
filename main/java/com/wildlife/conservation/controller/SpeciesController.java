package com.wildlife.conservation.controller;

import com.wildlife.conservation.entity.Species;
import com.wildlife.conservation.service.SpeciesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/species")
@Tag(name = "Species", description = "Manage animal species")
public class SpeciesController {

    private final SpeciesService speciesService;

    public SpeciesController(SpeciesService speciesService) {
        this.speciesService = speciesService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('RANGER','ADMIN')")
    public ResponseEntity<List<Species>> getAllSpecies() {
        return ResponseEntity.ok(speciesService.getAllSpecies());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('RANGER','ADMIN')")
    public ResponseEntity<Species> getSpecies(@PathVariable Long id) {
        return ResponseEntity.ok(speciesService.getSpeciesById(id));
    }

    @GetMapping("/endangered")
    @PreAuthorize("hasAnyRole('RANGER','ADMIN')")
    public ResponseEntity<List<Species>> getEndangered() {
        return ResponseEntity.ok(speciesService.getEndangeredSpecies());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Species> createSpecies(@Valid @RequestBody Species species) {
        return ResponseEntity.status(HttpStatus.CREATED).body(speciesService.createSpecies(species));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Species> updateSpecies(@PathVariable Long id, @Valid @RequestBody Species species) {
        return ResponseEntity.ok(speciesService.updateSpecies(id, species));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSpecies(@PathVariable Long id) {
        speciesService.deleteSpecies(id);
        return ResponseEntity.noContent().build();
    }
}