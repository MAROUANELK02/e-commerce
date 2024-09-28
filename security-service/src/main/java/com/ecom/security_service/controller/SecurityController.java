package com.ecom.security_service.controller;

import com.ecom.security_service.dto.ChangePassword;
import com.ecom.security_service.dto.LoginDTO;
import com.ecom.security_service.dto.SignUpDTO;
import com.ecom.security_service.services.UserCredentialsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class SecurityController {
    private final UserCredentialsService userCredentialsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(userCredentialsService.login(loginDTO));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpDTO signUpDTO) {
        try {
            userCredentialsService.createUserCredentialsWithUserRole(signUpDTO);
            return ResponseEntity.ok("User added Successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword) {
        try {
            userCredentialsService.changePassword(changePassword.userId(),
                    changePassword.oldPassword(), changePassword.newPassword());
            return ResponseEntity.ok("Password changed Successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
