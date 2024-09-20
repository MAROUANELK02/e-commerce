package com.ecom.user_service.repositories;


import com.ecom.user_service.entities.Role;
import com.ecom.user_service.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ERole roleName);
}
