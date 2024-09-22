package com.ecom.order_service.services;

import com.ecom.order_service.dtos.OrderItemDTORequest;
import com.ecom.order_service.entities.Order;
import com.ecom.order_service.enums.OrderStatus;
import com.ecom.order_service.exceptions.InsufficientStockException;
import com.ecom.order_service.exceptions.OrderNotFoundException;

import java.util.List;

public interface OrderService {
    String createOrder(long userId, List<OrderItemDTORequest> orderItems) throws OrderNotFoundException, InsufficientStockException;
    Order getOrder(long orderId) throws OrderNotFoundException;
    Order changeOrderStatus(long orderId, OrderStatus orderStatus) throws OrderNotFoundException;
}
