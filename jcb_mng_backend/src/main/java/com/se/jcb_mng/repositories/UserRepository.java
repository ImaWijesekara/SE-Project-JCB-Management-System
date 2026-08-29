package com.se.jcb_mng.repositories;

import com.se.jcb_mng.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Boot automatically generates the SQL to find a user by username
    Optional<User> findByUsername(String username);

    // Auto-generates SQL to find a user by email
    Optional<User> findByEmail(String email);

    // Helpful for registration validation to prevent duplicate accounts
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
