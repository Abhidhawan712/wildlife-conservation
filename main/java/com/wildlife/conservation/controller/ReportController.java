package com.wildlife.conservation.controller;

import com.wildlife.conservation.dto.ReportResponse;
import com.wildlife.conservation.service.ObservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reports", description = "Wildlife observation reports and alerts")
public class ReportController {

    private final ObservationService observationService;

    public ReportController(ObservationService observationService) {
        this.observationService = observationService;
    }

    @GetMapping("/summary")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Generate summary report with counts per species and endangered alerts")
    public ResponseEntity<ReportResponse> getSummaryReport(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        return ResponseEntity.ok(observationService.generateReport(start, end));
    }
}