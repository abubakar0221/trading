package com.example.demo.controller;

import com.example.demo.model.AdminSession;
import com.example.demo.service.AdminSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminSessionController {

    @Autowired
    private AdminSessionService sessionService;

    @PostMapping("/signin-session")
    public ResponseEntity<?> signin(@RequestBody Map<String, String> request) {
        String userName = request.get("userName");
        String userId = request.get("userId");

        if (userName == null || userId == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Required data missing"));
        }

        sessionService.handleAdminSession(userName, userId, "ADMIN");
        return ResponseEntity.ok(Collections.singletonMap("message", "Admin session started"));
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

    @GetMapping("/{userId}")
    public ResponseEntity<?> getSession(@PathVariable String userId) {
        AdminSession session = sessionService.getLatestSession(userId);

        if (session == null) {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "No session found"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("userName", session.getUserName());
        response.put("userId", session.getUserId());
        response.put("signinTime", session.getSigninTime());
        response.put("logoutTime", session.getLogoutTime());
        response.put("isActive", session.isActive());
        response.put("role", session.getRole());

        return ResponseEntity.ok(response);
    }
}
