package com.ecom.inventory_service.kafka;

import com.ecom.inventory_service.dtos.OrderCreated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InventoryConsumer {

    @KafkaListener(topics = "${topic}", groupId = "${group-id}")
    public void consumeOrderCreated(OrderCreated order) {
        log.info("Order created: {}", order);
    }
}
