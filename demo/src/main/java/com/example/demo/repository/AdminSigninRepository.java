package com.example.demo.repository;

import com.example.demo.model.AdminSignin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdminSigninRepository extends JpaRepository<AdminSignin, Long> {
    Optional<AdminSignin> findByUserName(String userName);

    Optional<AdminSignin> findByEmail(String email);

    Optional<AdminSignin> findByPhone(String phone);
}
