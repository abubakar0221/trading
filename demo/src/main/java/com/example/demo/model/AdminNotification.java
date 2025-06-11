package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class AdminNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private String userName;
    private String userId;
    private LocalDateTime createdAt;
    private boolean isRead;

    // Constructors
    public AdminNotification() {
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
    }

    public AdminNotification(String message, String userName, String userId) {
        this.message = message;
        this.userName = userName;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
    }

    // Getters and Setters
    // Getters
    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isRead() {
        return isRead;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
