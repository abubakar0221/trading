package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.AdminNotification;
import com.example.demo.service.AdminNotificationService;

@RestController
@RequestMapping("/admin-notifications")
@CrossOrigin("*")
public class AdminNotificationController {

    @Autowired
    private AdminNotificationService adminNotificationService;

    @GetMapping("/unread")
    public List<AdminNotification> getUnreadNotifications() {
        return adminNotificationService.getUnreadNotifications();
    }

    // Mark Notification as Read
    @PostMapping("/mark-as-read/{id}")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Long id) {
        boolean updated = adminNotificationService.markAsRead(id);
        if (updated) {
            return ResponseEntity.ok(Map.of("message", "Notification marked as read"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Notification not found"));
        }
    }
}
