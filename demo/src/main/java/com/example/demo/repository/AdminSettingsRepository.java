package com.example.demo.repository;

import com.example.demo.model.AdminSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminSettingsRepository extends JpaRepository<AdminSettings, String> {
    AdminSettings findByUserId(String userId);
}
