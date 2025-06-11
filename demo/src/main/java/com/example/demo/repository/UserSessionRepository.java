package com.example.demo.repository;

import com.example.demo.model.UserSession;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    UserSession findTopByUserIdOrderBySigninTimeDesc(String userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_sessions SET userName = ?2 WHERE userId = ?1", nativeQuery = true)
    void updateUserNameByUserId(String userId, String newUserName);

}
