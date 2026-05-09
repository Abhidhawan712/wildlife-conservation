package com.wildlife.conservation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "rangers")
@Data
public class Ranger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Badge number is required")
    @Column(name = "badge_number", unique = true, nullable = false)
    private String badgeNumber;

    @Email(message = "Email must be valid")
    @Column(unique = true)
    private String email;

    private String contactNumber;

    @Column(nullable = false)
    private String role = "RANGER";

    // WRITE_ONLY means:
    // - Postman CAN send password in request body ✓
    // - Response JSON will NOT show the password ✓
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "ranger",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    private List<Observation> observations;
}