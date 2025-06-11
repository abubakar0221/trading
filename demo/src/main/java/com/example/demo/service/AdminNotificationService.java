package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AdminNotification;
import com.example.demo.repository.AdminNotificationRepository;

@Service
public class AdminNotificationService {

    @Autowired
    private AdminNotificationRepository adminNotificationRepository;

    public List<AdminNotification> getUnreadNotifications() {
        return adminNotificationRepository.findByIsReadFalse();
    }

    public boolean markAsRead(Long id) {
        Optional<AdminNotification> optionalNotification = adminNotificationRepository.findById(id);
        if (optionalNotification.isPresent()) {
            AdminNotification notification = optionalNotification.get();
            notification.setIsRead(true); // ✅ Set isRead to true
            adminNotificationRepository.save(notification);
            return true;
        }
        return false;
    }

    public void createNotification(String message, String userName, String userId) {
        AdminNotification notification = new AdminNotification(message, userName, userId);
        adminNotificationRepository.save(notification);
    }
}
