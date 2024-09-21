package com.ecom.order_service.kafka;

import com.ecom.order_service.entities.Order;
import com.ecom.order_service.enums.OrderStatus;
import com.ecom.order_service.exceptions.OrderNotFoundException;
import com.ecom.order_service.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class OrderConsumer {
    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "${listening-topic}", groupId = "${group-id}")
    public void listen(String response) {
        try {
            String id = response.substring(13);
            Order order = orderService.getOrder(Long.parseLong(id));
            if (response.startsWith("SUCCESS")) {
                Order changed = orderService.changeOrderStatus(order.getOrderId(),
                        OrderStatus.COMPLETED);
                kafkaProducer.sendToPayment(changed);
            } else {
                orderService.changeOrderStatus(order.getOrderId(), OrderStatus.CANCELLED);
            }
        } catch (OrderNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
