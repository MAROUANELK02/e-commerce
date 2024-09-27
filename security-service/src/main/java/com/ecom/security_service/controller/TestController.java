package com.ecom.security_service.controller;

import com.ecom.security_service.dto.LoginDTO;
import com.ecom.security_service.services.UserCredentialsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class TestController {
    private final UserCredentialsService userCredentialsService;

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public String get(Authentication authentication) {
        String collect = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining("  "));
        return "Hello World " + collect;
    }

    @PostMapping("/token")
    public ResponseEntity<?> jwtToken(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userCredentialsService.login(loginDTO));
    }
}
