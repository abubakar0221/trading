package com.example.demo.service;

import com.example.demo.model.UserSession;
import com.example.demo.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserSessionService {

    @Autowired
    private UserSessionRepository sessionRepo;

    public void handleUserSession(String userName, String userId, String role) {
        // Check if there is an existing active session
        UserSession existing = sessionRepo.findTopByUserIdOrderBySigninTimeDesc(userId);

        if (existing != null && !existing.isActive()) {
            // Update same row if inactive session exists
            existing.setSigninTime(LocalDateTime.now());
            existing.setActive(true);
            existing.setLogoutTime(null); // reset logout
            sessionRepo.save(existing);
        } else if (existing == null) {
            // New user - create new session
            UserSession session = new UserSession();
            session.setUserName(userName);
            session.setUserId(userId);
            session.setSigninTime(LocalDateTime.now());
            session.setActive(true);
            session.setRole(role);
            sessionRepo.save(session);
        } else {
            // Same user active session: update with fresh signinTime
            existing.setSigninTime(LocalDateTime.now());
            sessionRepo.save(existing);
        }
    }

    public boolean updateLogoutTime(String userId) {
        try {
            System.out.println("Attempting logout for userId: " + userId);
            UserSession session = sessionRepo.findTopByUserIdOrderBySigninTimeDesc(userId);

            if (session != null) {
                System.out.println("Session found: " + session.getId() + ", isActive: " + session.isActive()); // ✅
                                                                                                               // Updated
                if (session.isActive()) { // ✅ Updated
                    LocalDateTime logoutTime = LocalDateTime.now();
                    System.out.println("Setting logout time to: " + logoutTime);
                    session.setLogoutTime(logoutTime);
                    session.setActive(false); // ✅ Updated
                    UserSession savedSession = sessionRepo.save(session);
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

    // Fetch Latest Session
    public UserSession getLatestSession(String userId) {
        try {
            return sessionRepo.findTopByUserIdOrderBySigninTimeDesc(userId);
        } catch (Exception e) {
            System.err.println("Error fetching latest session: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
