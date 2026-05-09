package com.wildlife.conservation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "species")
@Data
public class Species {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Species name is required")
    @Column(unique = true, nullable = false)
    private String name;

    private String scientificName;

    private String habitat;

    @Column(nullable = false)
    private boolean endangered = false;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "species", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Observation> observations;
}