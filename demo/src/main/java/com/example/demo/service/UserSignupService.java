package com.example.demo.service;

import com.example.demo.model.UserSignup;
import com.example.demo.model.UserSignupRequest;
import com.example.demo.repository.UserSignupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserSignupService {

    @Autowired
    private UserSignupRepository userSignupRepository;

    public UserSignup registerUser(UserSignupRequest request) {
        // Generate unique userId and referralCode
        String generatedUserId = generateUserId();
        String generatedReferralCode = generateReferralCode(); // ✅ Corrected

        // ✅ Using setters to avoid constructor issues
        UserSignup newUser = new UserSignup();
        newUser.setUserId(generatedUserId);
        newUser.setUserName(request.getUserName());
        newUser.setEmail(request.getEmail());
        newUser.setPhone(request.getPhone());
        newUser.setTradingId(request.getTradingId());
        newUser.setPassword(request.getPassword()); // ✅ Stores plain password as requested
        newUser.setReferralId(request.getReferralId()); // Nullable
        newUser.setReferralCode(generatedReferralCode); // ✅ Now correctly defined
        newUser.setRole("USER"); // Default role
        newUser.setCreatedAt(LocalDateTime.now()); // Timestamp

        return userSignupRepository.save(newUser);
    }

    // Function to generate a unique User ID (e.g., USER_0001, USER_0002)
    private String generateUserId() {
        long count = userSignupRepository.count() + 1;
        return String.format("USER_%04d", count);
    }

    // Function to generate a unique Referral Code
    private String generateReferralCode() {
        return "REF_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<UserSignup> getAllUsers() {
        return userSignupRepository.findAll();
    }

    public List<UserSignup> getRecentUsers() {
        return userSignupRepository.findTop20ByOrderByCreatedAtDesc();
    }

    @Autowired
    private UserSignupRepository repo;

    public UserSignup getUserProfile(String userId) {
        return repo.findByUserId(userId).orElse(null);
    }
}
