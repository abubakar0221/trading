package com.example.demo.service;

import com.example.demo.model.AdminSettings;
import com.example.demo.repository.AdminSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.AdminSessionRepository;

@Service
public class AdminSettingsService {

    @Autowired
    private AdminSettingsRepository adminSettingsRepository;

    @Autowired
    private AdminSessionRepository adminSessionRepository;

    public boolean updateUsername(String userId, String newUsername) {
        AdminSettings admin = adminSettingsRepository.findByUserId(userId);
        if (admin != null) {
            admin.setUserName(newUsername);
            adminSettingsRepository.save(admin);

            // Update admin name in sessions table too
            adminSessionRepository.updateUserNameByUserId(userId, newUsername);

            return true;
        }
        return false;
    }

    public boolean updatePhone(String userId, String newPhone) {
        AdminSettings admin = adminSettingsRepository.findByUserId(userId);
        if (admin != null) {
            admin.setPhone(newPhone);
            adminSettingsRepository.save(admin);
            return true;
        }
        return false;
    }
}
