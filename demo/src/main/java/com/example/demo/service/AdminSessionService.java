package com.example.demo.service;

import com.example.demo.model.AdminSession;
import com.example.demo.repository.AdminSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminSessionService {

    @Autowired
    private AdminSessionRepository sessionRepo;

    public void handleAdminSession(String userName, String userId, String role) {
        AdminSession existing = sessionRepo.findTopByUserIdOrderBySigninTimeDesc(userId);

        if (existing != null && !existing.isActive()) {
            existing.setSigninTime(LocalDateTime.now());
            existing.setActive(true);
            existing.setLogoutTime(null);
            sessionRepo.save(existing);
        } else if (existing == null) {
            AdminSession session = new AdminSession();
            session.setUserName(userName);
            session.setUserId(userId);
            session.setSigninTime(LocalDateTime.now());
            session.setActive(true);
            session.setRole(role);
            sessionRepo.save(session);
        } else {
            existing.setSigninTime(LocalDateTime.now());
            sessionRepo.save(existing);
        }
    }

    public boolean updateLogoutTime(String userId) {
        try {
            System.out.println("Attempting logout for userId: " + userId);
            AdminSession session = sessionRepo.findTopByUserIdOrderBySigninTimeDesc(userId);

            if (session != null) {
                System.out.println("Session found: " + session.getId() + ", isActive: " + session.isActive()); // ✅
                                                                                                               // Updated
                if (session.isActive()) { // ✅ Updated
                    LocalDateTime logoutTime = LocalDateTime.now();
                    System.out.println("Setting logout time to: " + logoutTime);
                    session.setLogoutTime(logoutTime);
                    session.setActive(false); // ✅ Updated
                    AdminSession savedSession = sessionRepo.save(session);
                    System.out.println("Saved session with logout time: " + savedSession.getLogoutTime());
                    return true;
                } else {
                    System.out.println("Session found, but not active.");
                    return false;
                }
            } else {
                System.out.println("No session found for logout for user: " + userId);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error updating logout time: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public AdminSession getLatestSession(String userId) {
        return sessionRepo.findTopByUserIdOrderBySigninTimeDesc(userId);
    }
}
