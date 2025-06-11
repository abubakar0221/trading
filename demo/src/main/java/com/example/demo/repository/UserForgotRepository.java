package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.UserForgot;

public interface UserForgotRepository extends JpaRepository<UserForgot, Long> {
    UserForgot findByEmail(String email);
}
