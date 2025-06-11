package com.example.demo.controller;

import com.example.demo.model.AdminSignin;
import com.example.demo.service.AdminSigninService;
import com.example.demo.service.AdminSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminSigninController {

    @Autowired
    private AdminSigninService adminSigninService;

    @Autowired
    private AdminSessionService adminSessionService;

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> loginAdmin(@RequestBody Map<String, String> loginRequest) {
        String identifier = loginRequest.get("identifier");
        String password = loginRequest.get("password");

        Map<String, String> response = new HashMap<>();

        if (identifier == null || password == null) {
            response.put("message", "❌ Username/Email/Phone and Password are required!");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<AdminSignin> adminOptional = adminSigninService.findAdminByIdentifier(identifier);

        if (adminOptional.isEmpty()) {
            response.put("message", "❌ Admin not found!");
            return ResponseEntity.status(404).body(response);
        }

        AdminSignin admin = adminOptional.get();

        if (!adminSigninService.verifyPassword(admin, password)) {
            response.put("message", "❌ Incorrect password!");
            return ResponseEntity.status(401).body(response);
        }

        if (!adminSigninService.isAdmin(admin)) {
            response.put("message", "⛔ Access Denied! You are not an admin.");
            return ResponseEntity.status(403).body(response);
        }

        // Handle admin session
        adminSessionService.handleAdminSession(admin.getUserName(), admin.getUserId(), admin.getRole());

        response.put("message", "✅ Admin login successful!");
        response.put("userName", admin.getUserName());
        response.put("userId", admin.getUserId());
        response.put("role", admin.getRole());

        return ResponseEntity.ok(response);
    }
}
