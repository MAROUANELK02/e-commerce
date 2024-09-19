package com.ecom.notification_service.kafka;

import com.ecom.notification_service.dtos.PaymentCreated;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@AllArgsConstructor
public class NotificationConsumer {

    @KafkaListener(topics = "${topic}", groupId = "${group-id}")
    public void consumeOrderCreated(PaymentCreated paymentCreated) {
        log.info("Order created: {}", paymentCreated);
    }
}
