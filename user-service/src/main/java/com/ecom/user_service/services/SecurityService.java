package com.ecom.user_service.services;

import com.ecom.user_service.dtos.SignUpDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "security-service")
public interface SecurityService {

    @PostMapping("/auth/signUp")
    void signUp(@RequestBody SignUpDTO signUpDTO);

    @PostMapping("/signUpVendor")
    void signUpVendor(@RequestBody SignUpDTO userCredentials);
}
