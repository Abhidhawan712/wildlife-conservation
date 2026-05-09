package com.wildlife.conservation;

import com.wildlife.conservation.entity.Ranger;
import com.wildlife.conservation.entity.Species;
import com.wildlife.conservation.repository.RangerRepository;
import com.wildlife.conservation.repository.SpeciesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class WildlifeConservationApplication {

    private static final Logger logger =
        LoggerFactory.getLogger(WildlifeConservationApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WildlifeConservationApplication.class, args);
    }

    @Bean
    public CommandLineRunner startupLoader(
            RangerRepository rangerRepository,
            SpeciesRepository speciesRepository) {

        return args -> {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // Admin ranger
            Ranger admin = new Ranger();
            admin.setName("Admin User");
            admin.setBadgeNumber("ADMIN001");
            admin.setEmail("admin@wildlife.com");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRole("ADMIN");
            rangerRepository.save(admin);

            // Normal ranger
            Ranger ranger = new Ranger();
            ranger.setName("John Ranger");
            ranger.setBadgeNumber("RNG001");
            ranger.setEmail("john@wildlife.com");
            ranger.setPassword(encoder.encode("ranger123"));
            ranger.setRole("RANGER");
            rangerRepository.save(ranger);

            // Species 1
            Species tiger = new Species();
            tiger.setName("Bengal Tiger");
            tiger.setScientificName("Panthera tigris tigris");
            tiger.setHabitat("Forest");
            tiger.setEndangered(true);
            tiger.setDescription("Endangered big cat");
            speciesRepository.save(tiger);

            // Species 2
            Species deer = new Species();
            deer.setName("Spotted Deer");
            deer.setScientificName("Axis axis");
            deer.setHabitat("Grassland");
            deer.setEndangered(false);
            deer.setDescription("Common deer species");
            speciesRepository.save(deer);

            logger.info("========================================");
            logger.info("  Wildlife Conservation System Started  ");
            logger.info("  Swagger: http://localhost:8080/swagger-ui.html");
            logger.info("  H2 Console: http://localhost:8080/h2-console");
            logger.info("========================================");
            logger.info("  ADMIN  → admin@wildlife.com / admin123");
            logger.info("  RANGER → john@wildlife.com  / ranger123");
            logger.info("========================================");
        };
    }
}