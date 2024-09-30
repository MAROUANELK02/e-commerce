package com.ecom.payment_service.controllers;

import com.ecom.payment_service.enums.PaymentStatus;
import com.ecom.payment_service.exceptions.PaymentNotFoundException;
import com.ecom.payment_service.paypal.PaypalService;
import com.ecom.payment_service.services.PaymentService;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PaymentController {
    private final PaypalService paypalService;
    private final PaymentService paymentService;

    @GetMapping("/redirectUrl/{orderId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<?> redirectUrl(@PathVariable long orderId) {
        try {
            return ResponseEntity.ok().body(paymentService.getRedirectUrlByOrderId(orderId));
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/executePayment/{orderId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<?> executePayment(@PathVariable(name = "orderId") long orderId,
                                            @RequestParam(name = "paymentId") String paymentId,
                                            @RequestParam(name = "payerId") String payerId) {
        try {
            paypalService.executePayment(paymentId, payerId);
            paymentService.confirmPaymentByOrderId(orderId);
            return ResponseEntity.ok().body("Payment successfully done");
        } catch (PayPalRESTException | PaymentNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/paymentStatus/{orderId}")
    @PreAuthorize("hasAuthority('SCOPE_USER') or hasAuthority('SCOPE_VENDOR') or hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> paymentStatus(@PathVariable(name = "orderId") long orderId) {
        try {
            PaymentStatus status = paymentService.getPaymentStatusByOrderId(orderId);
            return ResponseEntity.ok().body(String.valueOf(status));
        } catch (PaymentNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
