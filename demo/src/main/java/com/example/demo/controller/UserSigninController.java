package com.example.demo.controller;

import com.example.demo.model.UserSignin;
import com.example.demo.service.UserSessionService;
import com.example.demo.service.UserSigninService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Adjust as needed
public class UserSigninController {

    @Autowired
    private UserSigninService userSigninService;

    @Autowired
    private UserSessionService userSessionService;

    @PostMapping("/signin")
    public Map<String, String> loginUser(@RequestBody Map<String, String> loginRequest) {
        String identifier = loginRequest.get("userid"); // userid from frontend
        String password = loginRequest.get("password");

        Optional<UserSignin> userOptional = userSigninService.findUserByIdentifier(identifier);
        Map<String, String> response = new HashMap<>();

        if (userOptional.isEmpty()) {
            response.put("message", "User not found!");
        } else {
            UserSignin user = userOptional.get();
            if (userSigninService.verifyPassword(user, password)) {
                // ✅ Record Signin Time in `user_sessions`

                userSessionService.handleUserSession(
                        user.getUserName(),
                        user.getUserId(),
                        user.getRole() != null ? user.getRole() : "USER");

                response.put("message", "Login successful");
                response.put("userName", user.getUserName());
                response.put("userId", user.getUserId());
                response.put("role", user.getRole());

            } else {
                response.put("message", "Incorrect password!");
            }
        }
        return response;

    }

}