package com.ecom.user_service;

import com.ecom.user_service.entities.Role;
import com.ecom.user_service.entities.User;
import com.ecom.user_service.enums.ERole;
import com.ecom.user_service.exceptions.RoleNotFoundException;
import com.ecom.user_service.repositories.RoleRepository;
import com.ecom.user_service.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	//@Bean
	CommandLineRunner commandLineRunner(UserService userService, RoleRepository roleRepository) {
		return args -> {
			if(roleRepository.findByRoleName(ERole.USER).isEmpty()) {
				roleRepository.save(new Role(1L, ERole.USER));
			}
			if(roleRepository.findByRoleName(ERole.VENDOR).isEmpty()) {
				roleRepository.save(new Role(2L, ERole.VENDOR));
			}
			if(roleRepository.findByRoleName(ERole.ADMIN).isEmpty()) {
				roleRepository.save(new Role(3L, ERole.ADMIN));
			}
			List<User> users = List.of(
					new User(1L, "mahmoud", "aji", "mahmodNj", "1234567", "mahmod-nj@example.com", null),
					new User(2L, "hanane", "fink", "hananeFn", "1234567", "hanane-fn@example.com", null),
					new User(3L, "samir", "sir", "samirSr", "1234567", "samir-sr@example.com", null));
			users.forEach(user -> {
                try {
                    userService.createUser(user);
                } catch (RoleNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
		};
	}

}
