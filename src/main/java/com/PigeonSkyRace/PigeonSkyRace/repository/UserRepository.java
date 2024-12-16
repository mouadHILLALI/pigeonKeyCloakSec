package com.PigeonSkyRace.PigeonSkyRace.repository;

import com.PigeonSkyRace.PigeonSkyRace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
}
