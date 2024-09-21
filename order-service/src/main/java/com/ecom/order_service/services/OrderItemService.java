package com.ecom.order_service.services;

import com.ecom.order_service.dtos.OrderItemDTORequest;
import com.ecom.order_service.entities.OrderItem;
import com.ecom.order_service.exceptions.OrderItemNotFoundException;
import com.ecom.order_service.exceptions.OrderNotFoundException;

import java.util.List;

public interface OrderItemService {
    OrderItem save(OrderItemDTORequest orderItem, long orderId) throws OrderNotFoundException;
    void saveAll(List<OrderItemDTORequest> orderItemDTORequests, long orderId) throws OrderNotFoundException;
    OrderItem findById(Long id) throws OrderItemNotFoundException;
}
