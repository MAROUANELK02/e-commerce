package com.ecom.inventory_service.dtos;

import com.ecom.inventory_service.enums.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderCreated {
    private Long orderId;
    private Long userId;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private List<OrderItemDTOResponse> orderItems = new ArrayList<>();
}
