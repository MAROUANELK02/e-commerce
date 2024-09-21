package com.ecom.order_service.mappers;

import com.ecom.order_service.dtos.OrderItemDTORequest;
import com.ecom.order_service.dtos.OrderItemDTOResponse;
import com.ecom.order_service.entities.OrderItem;
import com.ecom.order_service.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemMapper {
    private final ObjectMapper objectMapper;
    private final ProductService productService;

    public OrderItem toOrderItem(OrderItemDTORequest orderItemDTORequest) {
        OrderItem orderItem = objectMapper.convertValue(orderItemDTORequest, OrderItem.class);
        orderItem.setPrice(productService.findProductPrice(orderItem.getProductId()));
        return orderItem;
    }

    public List<OrderItem> toOrderItems(List<OrderItemDTORequest> orderItemDTORequests) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTORequest orderItemDTORequest : orderItemDTORequests) {
            orderItems.add(toOrderItem(orderItemDTORequest));
        }
        return orderItems;
    }

    public OrderItemDTOResponse toOrderItemDTOResponse(OrderItem orderItem) {
        return objectMapper.convertValue(orderItem, OrderItemDTOResponse.class);
    }

    public List<OrderItemDTOResponse> toOrderItemDTOResponses(List<OrderItem> orderItems) {
        List<OrderItemDTOResponse> orderItemDTOResponses = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            orderItemDTOResponses.add(toOrderItemDTOResponse(orderItem));
        }
        return orderItemDTOResponses;
    }
}
