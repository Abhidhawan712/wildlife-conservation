package com.wildlife.conservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ReportResponse {
    private String startDate;
    private String endDate;
    private Map<String, Long> observationsPerSpecies;
    private List<String> endangeredAlerts;
    private long totalObservations;
}