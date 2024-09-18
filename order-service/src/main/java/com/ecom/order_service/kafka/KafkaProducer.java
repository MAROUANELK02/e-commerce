package com.ecom.order_service.kafka;

import com.ecom.order_service.dtos.OrderCreated;
import com.ecom.order_service.entities.Order;
import com.ecom.order_service.mappers.OrderMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private final OrderMapper orderMapper;
    private final KafkaTemplate<String, OrderCreated> kafkaTemplate;

    @Value("${topic}")
    private String topic;

    public KafkaProducer(OrderMapper orderMapper, KafkaTemplate<String, OrderCreated> kafkaTemplate) {
        this.orderMapper = orderMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Order order) {

        Message<OrderCreated> message = MessageBuilder.withPayload(orderMapper.toOrderCreated(order))
                .setHeader(KafkaHeaders.TOPIC, topic).build();

        kafkaTemplate.send(message);
    }
}
