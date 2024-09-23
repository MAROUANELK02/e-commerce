package com.ecom.payment_service.dtos;

import com.ecom.payment_service.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Long paymentId;
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private PaymentStatus status;
    private String redirectUrl;
}