package com.example.demo.repository;

import com.example.demo.model.AdminSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AdminSessionRepository extends JpaRepository<AdminSession, Long> {
    AdminSession findTopByUserIdOrderBySigninTimeDesc(String userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_sessions SET userName = ?2 WHERE userId = ?1", nativeQuery = true)
    void updateUserNameByUserId(String userId, String newUserName);
}
