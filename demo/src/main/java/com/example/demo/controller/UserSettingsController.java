package com.example.demo.controller;

import com.example.demo.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserSettingsController {

    @Autowired
    private UserSettingsService userSettingsService;

    @PostMapping("/update-username")
    public ResponseEntity<Map<String, String>> updateUsername(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String newUsername = request.get("userName");

        Map<String, String> response = new HashMap<>();
        if (userSettingsService.updateUsername(userId, newUsername)) {
            response.put("status", "success");
            response.put("message", "Username updated successfully.");
        } else {
            response.put("status", "error");
            response.put("message", "User not found.");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-phone")
    public ResponseEntity<Map<String, String>> updatePhone(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String newPhone = request.get("phone");

        Map<String, String> response = new HashMap<>();
        if (userSettingsService.updatePhone(userId, newPhone)) {
            response.put("status", "success");
            response.put("message", "Phone number updated successfully.");
        } else {
            response.put("status", "error");
            response.put("message", "User not found.");
        }
        return ResponseEntity.ok(response);
    }
}
