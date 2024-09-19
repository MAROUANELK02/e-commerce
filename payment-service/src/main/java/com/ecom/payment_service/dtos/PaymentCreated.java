package com.ecom.payment_service.dtos;

import com.ecom.payment_service.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentCreated {
    private Long paymentId;
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private PaymentStatus status;
}
