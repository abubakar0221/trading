package com.example.demo.repository;

import com.example.demo.model.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingsRepository extends JpaRepository<UserSettings, String> {
    UserSettings findByUserId(String userId);
}
