package com.example.demo.service;

import com.example.demo.model.UserSignin;
import com.example.demo.repository.UserSigninRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSigninService {

    @Autowired
    private UserSigninRepository userSigninRepository;

    public Optional<UserSignin> findUserByIdentifier(String identifier) {
        System.out.println("🔍 Searching for user with identifier: " + identifier);

        Optional<UserSignin> userByUserName = userSigninRepository.findByUserName(identifier);
        if (userByUserName.isPresent()) {
            System.out.println("✅ User found by username");
            return userByUserName;
        }

        Optional<UserSignin> userByEmail = userSigninRepository.findByEmail(identifier);
        if (userByEmail.isPresent()) {
            System.out.println("✅ User found by email");
            return userByEmail;
        }

        Optional<UserSignin> userByPhone = userSigninRepository.findByPhone(identifier);
        if (userByPhone.isPresent()) {
            System.out.println("✅ User found by phone");
            return userByPhone;
        }

        System.out.println("❌ User not found in database.");
        return Optional.empty();
    }

    public boolean verifyPassword(UserSignin user, String password) {
        return user.getPassword().equals(password);
    }

}
