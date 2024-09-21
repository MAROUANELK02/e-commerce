package com.ecom.order_service.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDTORequest {
    private Long userId;
    private List<OrderItemDTORequest> orderItems = new ArrayList<>();
}
