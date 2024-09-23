package com.ecom.order_service.services;

import com.ecom.order_service.dtos.OrderItemDTORequest;
import com.ecom.order_service.entities.Order;
import com.ecom.order_service.enums.OrderStatus;
import com.ecom.order_service.exceptions.InsufficientStockException;
import com.ecom.order_service.exceptions.OrderNotFoundException;
import com.ecom.order_service.kafka.KafkaProducer;
import com.ecom.order_service.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final KafkaProducer kafkaProducer;
    private final InventoryService inventoryService;

    @Override
    public String createOrder(long userId, List<OrderItemDTORequest> orderItems) throws OrderNotFoundException, InsufficientStockException {
        for(OrderItemDTORequest orderItem : orderItems) {
            if(!inventoryService.checkAvailability(orderItem.getProductId(), orderItem.getQuantity())) {
                throw new InsufficientStockException("Insufficient Stock");
            }
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        Order save = orderRepository.save(order);
        Order saved = orderItemService.saveAll(orderItems, save.getOrderId());
        kafkaProducer.send(saved);
        return "Order Created "+saved.getOrderId();
    }

    @Override
    public Order getOrder(long orderId) throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        }else{
            throw new OrderNotFoundException("Order not found");
        }
    }

    @Override
    public Order changeOrderStatus(long orderId, OrderStatus orderStatus) throws OrderNotFoundException {
        Order order = getOrder(orderId);
        order.setStatus(orderStatus);
        return orderRepository.save(order);
    }
}
