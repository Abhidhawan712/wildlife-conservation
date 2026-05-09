package com.wildlife.conservation.service;

import com.wildlife.conservation.entity.Ranger;
import com.wildlife.conservation.exception.ResourceNotFoundException;
import com.wildlife.conservation.repository.RangerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RangerService {

    private final RangerRepository rangerRepository;

    public RangerService(RangerRepository rangerRepository) {
        this.rangerRepository = rangerRepository;
    }

    public List<Ranger> getAllRangers() {
        return rangerRepository.findAll();
    }

    public Ranger getRangerById(Long id) {
        return rangerRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Ranger not found with id: " + id));
    }

    public Ranger createRanger(Ranger ranger) {
        // Encode password directly — avoids circular dependency
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        ranger.setPassword(encoder.encode(ranger.getPassword()));
        return rangerRepository.save(ranger);
    }

    public Ranger updateRanger(Long id, Ranger updated) {
        Ranger existing = getRangerById(id);
        existing.setName(updated.getName());
        existing.setContactNumber(updated.getContactNumber());
        existing.setEmail(updated.getEmail());
        if (updated.getRole() != null) {
            existing.setRole(updated.getRole());
        }
        return rangerRepository.save(existing);
    }

    public void deleteRanger(Long id) {
        Ranger ranger = getRangerById(id);
        rangerRepository.delete(ranger);
    }
}