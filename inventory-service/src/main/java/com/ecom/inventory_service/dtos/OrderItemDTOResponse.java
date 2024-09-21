package com.ecom.inventory_service.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTOResponse {
    private Long orderItemId;
    private Long productId;
    private int quantity;
    private BigDecimal price;
}
