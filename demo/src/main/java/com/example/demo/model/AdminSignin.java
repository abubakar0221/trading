package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users") // Matching your table name exactly
public class AdminSignin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName; // Matching "userName" from SQL table

    @Column(nullable = false, unique = true)
    private String email; // Matching "email" from SQL table

    @Column(nullable = false, unique = true)
    private String phone; // Matching "phone" from SQL table

    @Column(nullable = false, unique = true)
    private String tradingId; // Matching "tradingId" from SQL table

    @Column(nullable = false)
    private String password; // Matching "password" from SQL table

    @Column
    private String referralId; // Can be NULL, matching "referralId"

    @Column(nullable = false, unique = true)
    private String userId; // Matching "userId" from SQL table

    @Column(nullable = false, unique = true)
    private String referralCode; // Matching "referralCode" from SQL table

    @Column(nullable = false)
    private LocalDateTime createdAt; // Matching "createdAt" from SQL table

    @Column(nullable = false)
    private String role; // Matching "role" from SQL table

    // Constructor to set default values
    public AdminSignin() {
        this.createdAt = LocalDateTime.now(); // Automatically set creation time
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTradingId() {
        return tradingId;
    }

    public void setTradingId(String tradingId) {
        this.tradingId = tradingId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
