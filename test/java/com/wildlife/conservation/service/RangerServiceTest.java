package com.wildlife.conservation.service;

import com.wildlife.conservation.entity.Ranger;
import com.wildlife.conservation.exception.ResourceNotFoundException;
import com.wildlife.conservation.repository.RangerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RangerServiceTest {

    @Mock private RangerRepository rangerRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private RangerService rangerService;

    private Ranger ranger;

    @BeforeEach
    void setUp() {
        ranger = new Ranger();
        ranger.setId(1L);
        ranger.setName("John Doe");
        ranger.setEmail("john@reserve.com");
        ranger.setBadgeNumber("B001");
        ranger.setPassword("rawPassword");
        ranger.setRole("RANGER");
    }

    @Test
    void testGetRangerById_Found() {
        when(rangerRepository.findById(1L)).thenReturn(Optional.of(ranger));
        Ranger result = rangerService.getRangerById(1L);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testGetRangerById_NotFound() {
        when(rangerRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> rangerService.getRangerById(99L));
    }

    @Test
    void testCreateRanger_EncodesPassword() {
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(rangerRepository.save(any(Ranger.class))).thenReturn(ranger);

        Ranger created = rangerService.createRanger(ranger);

        verify(passwordEncoder).encode("rawPassword");
        verify(rangerRepository).save(ranger);
        assertNotNull(created);
    }

    @Test
    void testDeleteRanger_CallsDelete() {
        when(rangerRepository.findById(1L)).thenReturn(Optional.of(ranger));
        rangerService.deleteRanger(1L);
        verify(rangerRepository).delete(ranger);
    }
}