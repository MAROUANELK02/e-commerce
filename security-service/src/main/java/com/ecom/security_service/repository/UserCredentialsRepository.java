package com.ecom.security_service.repository;

import com.ecom.security_service.entities.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    UserCredentials findByUserNameOrEmail(String userName, String email);
}
