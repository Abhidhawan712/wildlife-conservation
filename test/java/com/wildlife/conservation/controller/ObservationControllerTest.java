package com.wildlife.conservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wildlife.conservation.dto.ObservationRequest;
import com.wildlife.conservation.entity.*;
import com.wildlife.conservation.service.ObservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ObservationController.class)
class ObservationControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private ObservationService observationService;

    @Test
    @WithMockUser(roles = "RANGER")
    void testGetAllObservations_Returns200() throws Exception {
        when(observationService.getAllObservations()).thenReturn(List.of());
        mockMvc.perform(get("/api/observations"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(roles = "RANGER")
    void testCreateObservation_Returns201() throws Exception {
        ObservationRequest req = new ObservationRequest();
        req.setObservationTime(LocalDateTime.now());
        req.setLocation("Zone B");
        req.setType("SIGHTING");
        req.setRangerId(1L);
        req.setSpeciesId(1L);
        req.setCount(1);

        Observation obs = new Observation();
        obs.setId(1L);
        obs.setLocation("Zone B");

        when(observationService.createObservation(any())).thenReturn(obs);

        mockMvc.perform(post("/api/observations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "RANGER")
    void testGetObservation_NotFound_Returns404() throws Exception {
        when(observationService.getObservationById(99L))
            .thenThrow(new com.wildlife.conservation.exception.ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/api/observations/99"))
            .andExpect(status().isNotFound());
    }
}