package com.wildlife.conservation.service;

import com.wildlife.conservation.entity.Species;
import com.wildlife.conservation.exception.ResourceNotFoundException;
import com.wildlife.conservation.repository.SpeciesRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SpeciesService {

    private final SpeciesRepository speciesRepository;

    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    public List<Species> getAllSpecies() {
        return speciesRepository.findAll();
    }

    public Species getSpeciesById(Long id) {
        return speciesRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Species not found with id: " + id));
    }

    public Species createSpecies(Species species) {
        return speciesRepository.save(species);
    }

    public Species updateSpecies(Long id, Species updated) {
        Species existing = getSpeciesById(id);
        existing.setName(updated.getName());
        existing.setScientificName(updated.getScientificName());
        existing.setHabitat(updated.getHabitat());
        existing.setEndangered(updated.isEndangered());
        existing.setDescription(updated.getDescription());
        return speciesRepository.save(existing);
    }

    public void deleteSpecies(Long id) {
        speciesRepository.delete(getSpeciesById(id));
    }

    public List<Species> getEndangeredSpecies() {
        return speciesRepository.findByEndangeredTrue();
    }
}