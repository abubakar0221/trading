package com.example.demo.controller;

import com.example.demo.service.AdminSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminSettingsController {

    @Autowired
    private AdminSettingsService adminSettingsService;

    @PostMapping("/update-username")
    public ResponseEntity<Map<String, String>> updateUsername(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String newUsername = request.get("userName");

        Map<String, String> response = new HashMap<>();
        if (adminSettingsService.updateUsername(userId, newUsername)) {
            response.put("status", "success");
            response.put("message", "Username updated successfully.");
        } else {
            response.put("status", "error");
            response.put("message", "Admin not found.");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-phone")
    public ResponseEntity<Map<String, String>> updatePhone(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String newPhone = request.get("phone");

        Map<String, String> response = new HashMap<>();
        if (adminSettingsService.updatePhone(userId, newPhone)) {
            response.put("status", "success");
            response.put("message", "Phone number updated successfully.");
        } else {
            response.put("status", "error");
            response.put("message", "Admin not found.");
        }
        return ResponseEntity.ok(response);
    }
}
