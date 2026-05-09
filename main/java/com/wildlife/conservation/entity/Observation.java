package com.wildlife.conservation.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "observations")
@Data
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Observation time is required")
    @Column(nullable = false)
    private LocalDateTime observationTime;

    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    private String location;

    @NotBlank(message = "Type is required")
    @Column(nullable = false)
    private String type;

    private String notes;

    private int count = 1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ranger_id", nullable = false)
    @JsonIgnoreProperties({"observations", "password", "hibernateLazyInitializer"})
    private Ranger ranger;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "species_id", nullable = false)
    @JsonIgnoreProperties({"observations", "hibernateLazyInitializer"})
    private Species species;
}