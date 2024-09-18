package com.ecom.order_service.kafka;

import com.ecom.order_service.dtos.OrderCreated;
import com.ecom.order_service.entities.Order;
import com.ecom.order_service.mappers.OrderMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final OrderMapper orderMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${topic}")
    private String topic;

    public KafkaProducer(OrderMapper orderMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.orderMapper = orderMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String order) {
        //kafkaTemplate.send(topic, orderMapper.toOrderCreated(order));
        kafkaTemplate.send(topic, order);
    }
}
