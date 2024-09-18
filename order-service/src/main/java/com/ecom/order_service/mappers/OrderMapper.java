package com.ecom.order_service.mappers;

import com.ecom.order_service.dtos.OrderCreated;
import com.ecom.order_service.entities.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(OrderCreated orderCreated) {
        Order order = new Order();
        BeanUtils.copyProperties(orderCreated, order);
        return order;
    }

    public OrderCreated toOrderCreated(Order order) {
        OrderCreated orderCreated = new OrderCreated();
        BeanUtils.copyProperties(order, orderCreated);
        return orderCreated;
    }
}
