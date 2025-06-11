package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.service.UserForgotService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*") // Allow frontend requests
public class UserForgotController {

    @Autowired
    private UserForgotService userForgotService;

    // Verify if email exists
    @PostMapping("/verify-email")
    public ResponseEntity<Map<String, String>> verifyEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        Map<String, String> response = new HashMap<>();
        if (userForgotService.checkEmailExists(email)) {
            response.put("status", "success");
            response.put("message", "Email found.");
        } else {
            response.put("status", "error");
            response.put("message", "Email not registered.");
        }
        return ResponseEntity.ok(response);
    }

    // Reset password
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newPassword = request.get("newPassword");

        Map<String, String> response = new HashMap<>();
        if (userForgotService.updatePassword(email, newPassword)) {
            response.put("status", "success");
            response.put("message", "Password updated successfully.");
        } else {
            response.put("status", "error");
            response.put("message", "Email not found.");
        }
        return ResponseEntity.ok(response);
    }
}
