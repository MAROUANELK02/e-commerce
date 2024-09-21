package com.ecom.order_service.services;

import com.ecom.order_service.dtos.OrderItemDTORequest;
import com.ecom.order_service.entities.Order;
import com.ecom.order_service.entities.OrderItem;
import com.ecom.order_service.exceptions.OrderItemNotFoundException;
import com.ecom.order_service.exceptions.OrderNotFoundException;
import com.ecom.order_service.mappers.OrderItemMapper;
import com.ecom.order_service.repositories.OrderItemRepository;
import com.ecom.order_service.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItem save(OrderItemDTORequest orderItem, long orderId) throws OrderNotFoundException {
        OrderItem item = orderItemMapper.toOrderItem(orderItem);
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            item.setOrder(order.get());
            return orderItemRepository.save(item);
        }else{
            throw new OrderNotFoundException("Order not found");
        }
    }

    @Override
    public void saveAll(List<OrderItemDTORequest> orderItemDTORequests, long orderId) throws OrderNotFoundException {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTORequest orderItemDTORequest : orderItemDTORequests) {
            orderItems.add(save(orderItemDTORequest, orderId));
        }
    }

    @Override
    public OrderItem findById(Long id) throws OrderItemNotFoundException {
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        if (orderItem.isPresent()) {
            return orderItem.get();
        }
        else {
            throw new OrderItemNotFoundException("Order Item not found");
        }
    }
}
