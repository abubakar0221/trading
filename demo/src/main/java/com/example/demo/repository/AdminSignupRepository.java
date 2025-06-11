package com.example.demo.repository;

import com.example.demo.model.AdminSignup;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminSignupRepository extends JpaRepository<AdminSignup, Long> {
    // Additional custom queries if needed

    Optional<AdminSignup> findByUserId(String userId);

    // ✅ Find a user by username
    Optional<AdminSignup> findByUserName(String userName);
}
