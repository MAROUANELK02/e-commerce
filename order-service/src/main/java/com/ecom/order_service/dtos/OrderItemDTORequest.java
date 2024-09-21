package com.ecom.order_service.dtos;

import lombok.Data;

@Data
public class OrderItemDTORequest {
    private Long productId;
    private int quantity;
}
