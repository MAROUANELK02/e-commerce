package com.ecom.order_service.kafka;

import com.ecom.order_service.dtos.PaymentCreated;
import com.ecom.order_service.enums.OrderStatus;
import com.ecom.order_service.exceptions.OrderNotFoundException;
import com.ecom.order_service.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class OrderConsumer {
    private final OrderService orderService;

    @KafkaListener(topics = "${consumer-topic}", groupId = "${group-id}")
    public void consumeOrderCreated(PaymentCreated paymentCreated) {
        try {
            orderService.changeOrderStatus(paymentCreated.getOrderId(), OrderStatus.COMPLETED);
        } catch (OrderNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
