package com.ecom.security_service;

import com.ecom.security_service.config.RsaKeysConfig;
import com.ecom.security_service.dto.SignUpDTO;
import com.ecom.security_service.entities.Role;
import com.ecom.security_service.entities.UserCredentials;
import com.ecom.security_service.enums.ERole;
import com.ecom.security_service.exceptions.UserCredentialsNotFoundException;
import com.ecom.security_service.repository.RoleRepository;
import com.ecom.security_service.repository.UserCredentialsRepository;
import com.ecom.security_service.services.UserCredentialsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeysConfig.class)
public class SecurityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityServiceApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner commandLineRunner(UserCredentialsService userCredentialsService,
										RoleRepository roleRepository) {
		return args -> {
			if(roleRepository.findByRoleName(ERole.USER).isEmpty()) {
				roleRepository.save(Role.builder().roleName(ERole.USER).build());
			}
			if(roleRepository.findByRoleName(ERole.ADMIN).isEmpty()) {
                roleRepository.save(Role.builder().roleName(ERole.ADMIN).build());
            }
			if(roleRepository.findByRoleName(ERole.VENDOR).isEmpty()) {
                roleRepository.save(Role.builder().roleName(ERole.VENDOR).build());
            }

			try {
				userCredentialsService.loadUserByUsername("admin");
			}catch (UserCredentialsNotFoundException exp) {
				userCredentialsService.createUserCredentialsWithVendorRole(new SignUpDTO(
						"admin", "admin",
						"marouanelk02@gmail.com", 1L));
			}

		};
	}
}
