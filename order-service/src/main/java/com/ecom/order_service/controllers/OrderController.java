package com.ecom.order_service.controllers;

import com.ecom.order_service.entities.Order;
import com.ecom.order_service.enums.OrderStatus;
import com.ecom.order_service.kafka.KafkaProducer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private KafkaProducer kafkaProducer;

    @GetMapping("/send")
    public String send() {
        try {

            Order order = Order.builder()
                    .orderId(1L)
                    .orderDate(LocalDateTime.now())
                    .totalAmount(BigDecimal.valueOf(1500.50))
                    .userId(1L)
                    .status(OrderStatus.PENDING)
                    .orderItems(null)
                    .build();

            kafkaProducer.send(order);
        }catch (Throwable ex) {
            return ex.getMessage();
        }
        return "Message sent";
    }
}