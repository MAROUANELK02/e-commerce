package com.ecom.payment_service.dtos;

import com.ecom.payment_service.enums.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@ToString
public class OrderCreated {
    private Long orderId;
    private Long userId;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private Map<Long, Integer> products = new HashMap<>();
}
