package com.wildlife.conservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ObservationRequest {

    @NotNull
    private LocalDateTime observationTime;

    @NotBlank
    private String location;

    @NotBlank
    private String type;  // SIGHTING or INCIDENT

    private String notes;

    private int count = 1;

    @NotNull
    private Long rangerId;

    @NotNull
    private Long speciesId;
}