package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserForgot;
import com.example.demo.repository.UserForgotRepository;

@Service
public class UserForgotService {

    @Autowired
    private UserForgotRepository userForgotRepository;

    // Check if email exists
    public boolean checkEmailExists(String email) {
        return userForgotRepository.findByEmail(email) != null;
    }

    // Update password (plain text)
    public boolean updatePassword(String email, String newPassword) {
        UserForgot user = userForgotRepository.findByEmail(email);
        if (user != null) {
            user.setPassword(newPassword); // Storing password as plain text
            userForgotRepository.save(user);
            return true;
        }
        return false;
    }
}
