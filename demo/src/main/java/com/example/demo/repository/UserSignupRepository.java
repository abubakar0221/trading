package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserSignup;

@Repository
public interface UserSignupRepository extends JpaRepository<UserSignup, Long> {

    // ✅ Find a user by email (used in login, forgot password)
    Optional<UserSignup> findByEmail(String email);

    // ✅ Find a user by user ID
    Optional<UserSignup> findByUserId(String userId);

    // ✅ Find a user by username
    Optional<UserSignup> findByUserName(String userName);

    List<UserSignup> findTop20ByOrderByCreatedAtDesc();
}
