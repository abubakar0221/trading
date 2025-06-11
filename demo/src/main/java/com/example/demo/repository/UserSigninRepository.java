package com.example.demo.repository;

import com.example.demo.model.UserSignin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSigninRepository extends JpaRepository<UserSignin, Long> {
    Optional<UserSignin> findByUserName(String userName);

    Optional<UserSignin> findByEmail(String email);

    Optional<UserSignin> findByPhone(String phone);
}
