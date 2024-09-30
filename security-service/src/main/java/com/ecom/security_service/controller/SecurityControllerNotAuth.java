package com.ecom.security_service.controller;

import com.ecom.security_service.dto.SignUpDTO;
import com.ecom.security_service.services.UserCredentialsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class SecurityControllerNotAuth {
    private final UserCredentialsService userCredentialsService;

    @PostMapping("/signUpVendor")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO signUpDTO) {
        try {
            userCredentialsService.createUserCredentialsWithVendorRole(signUpDTO);
            return ResponseEntity.ok("Vendor added Successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
