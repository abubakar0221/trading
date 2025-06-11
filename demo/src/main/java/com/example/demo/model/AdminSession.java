package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_sessions")
public class AdminSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String userId;
    private LocalDateTime signinTime;
    private LocalDateTime logoutTime;
    private boolean isActive;

    private String role = "ADMIN";

    public AdminSession() {
    }

    public AdminSession(String userName, String userId, LocalDateTime signinTime, String role) {
        this.userName = userName;
        this.userId = userId;
        this.signinTime = signinTime;
        this.isActive = true;
        this.role = role;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getSigninTime() {
        return signinTime;
    }

    public void setSigninTime(LocalDateTime signinTime) {
        this.signinTime = signinTime;
    }

    public LocalDateTime getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(LocalDateTime logoutTime) {
        this.logoutTime = logoutTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
