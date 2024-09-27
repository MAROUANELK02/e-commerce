package com.ecom.security_service;

import com.ecom.security_service.config.RsaKeysConfig;
import com.ecom.security_service.entities.Role;
import com.ecom.security_service.entities.UserCredentials;
import com.ecom.security_service.enums.ERole;
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

	//@Bean
	CommandLineRunner commandLineRunner(UserCredentialsRepository userCredentialsService,
										RoleRepository roleRepository,
										PasswordEncoder passwordEncoder) {
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

			List<UserCredentials> userCredentials = List.of(
					UserCredentials.builder()
							.email("mahmod-nj@example.com").userName("mahmoud")
							.password(passwordEncoder.encode("1234"))
							.userId(1L).role(roleRepository.findById(1L).orElse(null))
							.build(),
					UserCredentials.builder()
							.email("hanane-fn@example.com").userName("hanane")
							.password(passwordEncoder.encode("1234"))
							.userId(2L).role(roleRepository.findById(2L).orElse(null))
							.build(),
					UserCredentials.builder()
							.email("samir-sr@example.com").userName("samir")
							.password(passwordEncoder.encode("1234"))
							.userId(3L).role(roleRepository.findById(3L).orElse(null))
							.build()
			);

			userCredentialsService.saveAll(userCredentials);
		};
	}
}
