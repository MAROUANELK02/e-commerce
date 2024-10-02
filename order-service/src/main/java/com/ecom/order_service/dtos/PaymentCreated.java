package com.ecom.order_service.dtos;

import com.ecom.order_service.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class PaymentCreated {
    private Long paymentId;
    private Long orderId;
    private Long userId;
    private String userAddress;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private PaymentStatus status;
}
