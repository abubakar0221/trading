package com.example.demo.service;

import com.example.demo.model.AdminSignup;
import com.example.demo.model.AdminSignupRequest;
import com.example.demo.model.UserSignup;
import com.example.demo.repository.AdminSignupRepository;
import com.example.demo.repository.UserSignupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AdminSignupService {

    @Autowired
    private AdminSignupRepository adminSignupRepository;

    public AdminSignup registerAdmin(AdminSignupRequest request) {
        // Generate unique userId and referralCode
        String generatedUserId = generateAdminUserId();
        String generatedReferralCode = generateReferralCode();

        // Creating new AdminSignup object with correct field names
        AdminSignup newAdmin = new AdminSignup();
        newAdmin.setUserId(generatedUserId);
        newAdmin.setUserName(request.getUserName());
        newAdmin.setEmail(request.getEmail());
        newAdmin.setPhone(request.getPhone());
        newAdmin.setTradingId(request.getTradingId());
        newAdmin.setPassword(request.getPassword()); // Storing plain password as requested
        newAdmin.setReferralId(request.getReferralId()); // Nullable
        newAdmin.setReferralCode(generatedReferralCode);
        newAdmin.setRole("ADMIN"); // Default role for Admin
        newAdmin.setCreatedAt(LocalDateTime.now()); // Timestamp

        return adminSignupRepository.save(newAdmin);
    }

    // Generate unique User ID for Admin (e.g., ADMIN_0001, ADMIN_0002)
    private String generateAdminUserId() {
        long count = adminSignupRepository.count() + 1;
        return String.format("ADMIN_%04d", count);
    }

    // Generate unique Referral Code for Admin
    private String generateReferralCode() {
        return "REF_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    @Autowired
    private AdminSignupRepository repo;

    public AdminSignup getUserProfile(String userId) {
        return repo.findByUserId(userId).orElse(null);
    }
}
