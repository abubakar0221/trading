package com.example.demo.service;

import com.example.demo.model.UserSettings;
import com.example.demo.repository.UserSessionRepository;
import com.example.demo.repository.UserSettingsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsService {

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    public boolean updateUsername(String userId, String newUsername) {
        UserSettings user = userSettingsRepository.findByUserId(userId);
        if (user != null) {
            user.setUserName(newUsername);
            userSettingsRepository.save(user);

            // Native SQL query to update username in user_sessions table
            userSessionRepository.updateUserNameByUserId(userId, newUsername);

            return true;
        }
        return false;
    }

    public boolean updatePhone(String userId, String newPhone) {
        UserSettings user = userSettingsRepository.findByUserId(userId);
        if (user != null) {
            user.setPhone(newPhone);
            userSettingsRepository.save(user);
            return true;
        }
        return false;
    }
}
