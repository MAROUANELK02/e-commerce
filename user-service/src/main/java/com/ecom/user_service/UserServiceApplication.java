package com.ecom.user_service;

import com.ecom.user_service.config.RsaKeysConfig;
import com.ecom.user_service.entities.User;
import com.ecom.user_service.exceptions.UserNotFoundException;
import com.ecom.user_service.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(RsaKeysConfig.class)
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserService userService) {
		return args -> {
			try {
				userService.getUserByEmail("marouanelk02@gmail.com");
			}catch (UserNotFoundException exp) {
				userService.createVendor(User.builder().userId(1L).first_name("admin")
						.last_name("admin").email("marouanelk02@gmail.com").build());
			}
		};
	}

}
