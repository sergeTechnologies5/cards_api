package com.logicea.cards.repository;

import com.logicea.cards.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
