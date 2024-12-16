package com.PigeonSkyRace.PigeonSkyRace.repository;

import com.PigeonSkyRace.PigeonSkyRace.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompetitionRepository extends JpaRepository<Competition, UUID> {
}
