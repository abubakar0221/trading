package com.example.demo.controller;

import com.example.demo.model.UserSignup;
import com.example.demo.model.UserSignupRequest;
import com.example.demo.repository.UserSignupRepository;
import com.example.demo.service.UserSignupService;
import com.example.demo.service.AdminNotificationService; // ✅ Import AdminNotificationService

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow frontend access
public class UserSignupController {

    @Autowired
    private UserSignupService userSignupService;

    @Autowired
    private UserSignupRepository userSignupRepository;

    @Autowired
    private AdminNotificationService adminNotificationService; // ✅ Inject Admin Notification Service

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String email) {
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        return ResponseEntity.ok("Email verified: " + email);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest userRequest) {
        try {
            UserSignup savedUser = userSignupService.registerUser(userRequest); // ✅ Register user

            // ✅ Create a notification for the admin
            String notificationMessage = "New user registered: " + savedUser.getUserName();
            adminNotificationService.createNotification(notificationMessage, savedUser.getUserName(),
                    savedUser.getUserId());

            // ✅ Create a response object
            Map<String, String> response = new HashMap<>();
            response.put("message", "Signup successful");
            response.put("userName", savedUser.getUserName());
            response.put("userId", savedUser.getUserId());
            response.put("referralCode", savedUser.getReferralCode());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<UserSignup>> getAllUsers() {
        List<UserSignup> users = userSignupService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/recent")
    public ResponseEntity<List<UserSignup>> getRecentUsers() {
        List<UserSignup> users = userSignupService.getRecentUsers();
        return ResponseEntity.ok(users);
    }

    @Autowired
    private UserSignupService service;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserSignup> getUserProfile(@PathVariable String userId) {
        UserSignup user = service.getUserProfile(userId);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
}
