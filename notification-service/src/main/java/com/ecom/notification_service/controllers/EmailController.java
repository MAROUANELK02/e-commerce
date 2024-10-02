package com.ecom.notification_service.controllers;

import com.ecom.notification_service.dtos.EmailMessage;
import com.ecom.notification_service.services.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class EmailController {
    private final EmailSenderService emailSenderService;

    @PostMapping("/email/send")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<?> sendEmail(@RequestBody EmailMessage emailMessage) {
        try {
            emailSenderService.sendEmail(emailMessage.destination(), emailMessage.subject(),
                    emailMessage.message());
            return ResponseEntity.ok("Email sent");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
