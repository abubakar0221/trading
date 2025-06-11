package com.example.demo.controller;

import com.example.demo.model.UserSession;
import com.example.demo.service.UserSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserSessionController {

    @Autowired
    private UserSessionService sessionService;

    @PostMapping("/signin-session")
    public ResponseEntity<?> signin(@RequestBody Map<String, String> request) {
        String userName = request.get("userName");
        String userId = request.get("userId");
        String role = request.getOrDefault("role", "USER");

        if (userName == null || userId == null || userName.isEmpty() || userId.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "Username and userId are required"));
        }

        sessionService.handleUserSession(userName, userId, role);
        return ResponseEntity.ok(Collections.singletonMap("message", "Session created successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");

        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "User ID is required"));
        }

        boolean success = sessionService.updateLogoutTime(userId);

        if (success) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Logout successful"));
        } else {
            return ResponseEntity.ok(Collections.singletonMap("message", "No active session found"));
        }
    }

    @GetMapping("/user_sessions/{userId}")
    public ResponseEntity<?> getSession(@PathVariable String userId) {
        UserSession session = sessionService.getLatestSession(userId);

        if (session == null) {
            return ResponseEntity.status(404)
                    .body(Collections.singletonMap("message", "No session found for user"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("userName", session.getUserName());
        response.put("userId", session.getUserId());
        response.put("signinTime", session.getSigninTime());
        response.put("logoutTime", session.getLogoutTime());
        response.put("isActive", session.isActive()); // ✅ Updated here
        response.put("role", session.getRole());

        return ResponseEntity.ok(response);
    }
}
