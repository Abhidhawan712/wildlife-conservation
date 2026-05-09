package com.wildlife.conservation.controller;

import com.wildlife.conservation.entity.Ranger;
import com.wildlife.conservation.service.RangerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rangers")
@Tag(name = "Rangers", description = "Manage wildlife rangers")
public class RangerController {

    private final RangerService rangerService;

    public RangerController(RangerService rangerService) {
        this.rangerService = rangerService;
    }

    @GetMapping
    @Operation(summary = "Get all rangers")
    @PreAuthorize("hasAnyRole('RANGER','ADMIN')")
    public ResponseEntity<List<Ranger>> getAllRangers() {
        return ResponseEntity.ok(rangerService.getAllRangers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('RANGER','ADMIN')")
    public ResponseEntity<Ranger> getRanger(@PathVariable Long id) {
        return ResponseEntity.ok(rangerService.getRangerById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Ranger> createRanger(@RequestBody Ranger ranger) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(rangerService.createRanger(ranger));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Ranger> updateRanger(
            @PathVariable Long id,
            @RequestBody Ranger ranger) {
        return ResponseEntity.ok(rangerService.updateRanger(id, ranger));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRanger(@PathVariable Long id) {
        rangerService.deleteRanger(id);
        return ResponseEntity.noContent().build();
    }
}