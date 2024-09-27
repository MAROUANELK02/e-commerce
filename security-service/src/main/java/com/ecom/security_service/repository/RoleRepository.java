package com.ecom.security_service.repository;

import com.ecom.security_service.entities.Role;
import com.ecom.security_service.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ERole roleName);
}
