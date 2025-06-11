package com.example.demo.service;

import com.example.demo.model.AdminSignin;

import com.example.demo.repository.AdminSigninRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminSigninService {

    @Autowired
    private AdminSigninRepository adminSigninRepository;

    public Optional<AdminSignin> findAdminByIdentifier(String identifier) {
        return adminSigninRepository.findByUserName(identifier)
                .or(() -> adminSigninRepository.findByEmail(identifier))
                .or(() -> adminSigninRepository.findByPhone(identifier));
    }

    public boolean verifyPassword(AdminSignin admin, String password) {
        return admin.getPassword().equals(password);
    }

    public boolean isAdmin(AdminSignin admin) {
        return "ADMIN".equalsIgnoreCase(admin.getRole());
    }
}
