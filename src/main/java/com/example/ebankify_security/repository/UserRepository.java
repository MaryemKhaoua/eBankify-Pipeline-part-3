package com.example.ebankify_security.repository;

import com.example.ebankify_security.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<Object> findByName(String name);
}