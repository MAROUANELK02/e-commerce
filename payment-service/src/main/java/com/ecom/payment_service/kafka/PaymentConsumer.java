package com.ecom.payment_service.kafka;

import com.ecom.payment_service.dtos.OrderCreated;
import com.ecom.payment_service.entities.Payment;
import com.ecom.payment_service.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class PaymentConsumer {
    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "${topic}", groupId = "${group-id}")
    public void consumeOrderCreated(OrderCreated order) {
        log.info("Order created: {}", order);
        Payment payment = Payment.builder()
                .orderId(order.getOrderId())
                .paymentId(1L)
                .paymentDate(LocalDateTime.now())
                .amount(order.getTotalAmount())
                .status(PaymentStatus.COMPLETED)
                .userId(order.getUserId())
                .build();
        kafkaProducer.send(payment);
    }
}