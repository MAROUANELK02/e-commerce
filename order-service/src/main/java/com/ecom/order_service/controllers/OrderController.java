package com.ecom.order_service.controllers;

import com.ecom.order_service.dtos.OrderItemDTORequest;
import com.ecom.order_service.exceptions.InsufficientStockException;
import com.ecom.order_service.exceptions.OrderNotFoundException;
import com.ecom.order_service.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order/{userId}")
    public ResponseEntity<?> createOrder(@PathVariable Long userId,
                                         @RequestBody List<OrderItemDTORequest> orderItems) {
        try {
            return ResponseEntity.ok().body(orderService.createOrder(userId, orderItems));
        } catch (OrderNotFoundException | InsufficientStockException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/orderStatus/{orderId}")
    public ResponseEntity<?> getOrderStatus(@PathVariable Long orderId) {
        try {
            return ResponseEntity.ok().body(orderService.getOrder(orderId).getStatus());
        } catch (OrderNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}