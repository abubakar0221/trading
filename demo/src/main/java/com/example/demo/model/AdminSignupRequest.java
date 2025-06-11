package com.example.demo.model;

public class AdminSignupRequest {
    private String userName; // Matches 'userName' in SQL
    private String email; // Matches 'email' in SQL
    private String phone; // Matches 'phone' in SQL
    private String tradingId; // Matches 'tradingId' in SQL
    private String password; // Matches 'password' in SQL
    private String referralId; // Matches 'referralId' in SQL (Nullable)
    private String referralCode; // Matches 'referralCode' in SQL
    private String role; // Matches 'role' in SQL

    // ✅ **Getters & Setters**
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

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
