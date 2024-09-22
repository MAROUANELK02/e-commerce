package com.ecom.payment_service.controllers;

import com.ecom.payment_service.enums.PaymentStatus;
import com.ecom.payment_service.exceptions.PaymentNotFoundException;
import com.ecom.payment_service.paypal.PaypalService;
import com.ecom.payment_service.services.PaymentService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PaymentController {
    private final PaypalService paypalService;
    private final PaymentService paymentService;

    @GetMapping("/redirectUrl/{userId}")
    public ResponseEntity<?> redirectUrl(@PathVariable long userId) {
        try {
            return ResponseEntity.ok().body(paymentService.getRedirectUrlByUserId(userId));
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/executePayment/{userId}")
    public ResponseEntity<?> executePayment(@PathVariable(name = "userId") long userId,
                                            @RequestParam(name = "paymentId") String paymentId,
                                            @RequestParam(name = "payerId") String payerId) {
        try {
            paypalService.executePayment(paymentId, payerId);
            paymentService.confirmPaymentByUserId(userId);
            return ResponseEntity.ok().body("Payment successfully done");
        } catch (PayPalRESTException | PaymentNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/paymentStatus/{userId}")
    public ResponseEntity<?> paymentStatus(@PathVariable(name = "userId") long userId) {
        try {
            PaymentStatus status = paymentService.getPaymentStatusByUserId(userId);
            return ResponseEntity.ok().body(String.valueOf(status));
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
