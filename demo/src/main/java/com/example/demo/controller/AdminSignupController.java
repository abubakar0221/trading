package com.example.demo.controller;

import com.example.demo.model.AdminSignup;
import com.example.demo.model.AdminSignupRequest;
import com.example.demo.service.AdminSignupService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*") // Allow frontend access
public class AdminSignupController {

    @Autowired
    private AdminSignupService adminSignupService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AdminSignupRequest adminRequest) {
        try {
            AdminSignup savedAdmin = adminSignupService.registerAdmin(adminRequest);

            // Response object with correct field names
            Map<String, String> response = new HashMap<>();
            response.put("message", "Admin Signup successful");
            response.put("userName", savedAdmin.getUserName());
            response.put("userId", savedAdmin.getUserId());
            response.put("referralCode", savedAdmin.getReferralCode());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}
