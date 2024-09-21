package com.ecom.order_service.mappers;

import com.ecom.order_service.dtos.OrderCreated;
import com.ecom.order_service.dtos.OrderDTORequest;
import com.ecom.order_service.entities.Order;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderMapper {
    private final OrderItemMapper orderItemMapper;

    public Order toOrder(OrderDTORequest orderDTORequest) {
        Order order = new Order();
        order.setUserId(orderDTORequest.getUserId());
        order.setOrderItems(orderItemMapper.toOrderItems(orderDTORequest.getOrderItems()));
        return order;
    }

    public OrderCreated toOrderCreated(Order order) {
        OrderCreated orderCreated = new OrderCreated();
        BeanUtils.copyProperties(order, orderCreated);
        orderCreated.setOrderItems(orderItemMapper.toOrderItemDTOResponses(order.getOrderItems()));
        return orderCreated;
    }
}
