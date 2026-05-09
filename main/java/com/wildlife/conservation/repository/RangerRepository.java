package com.wildlife.conservation.repository;

import com.wildlife.conservation.entity.Ranger;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RangerRepository extends JpaRepository<Ranger, Long> {
    Optional<Ranger> findByEmail(String email);
    Optional<Ranger> findByBadgeNumber(String badgeNumber);
}