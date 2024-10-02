package com.ecom.order_service.kafka;

import com.ecom.order_service.dtos.PaymentCreated;
import com.ecom.order_service.enums.OrderStatus;
import com.ecom.order_service.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class OrderConsumer {
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "${consumer-topic}", groupId = "${group-id}")
    public void consumeOrderCreated(PaymentCreated paymentCreated) {
        orderRepository.findById(paymentCreated.getOrderId()).ifPresent(order -> {
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
            System.out.println("Order "+ paymentCreated.getOrderId()+" Completed.");
        });
    }
}
