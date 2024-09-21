package com.ecom.order_service.exceptions;

public class OrderItemNotFoundException extends Throwable {
    public OrderItemNotFoundException(String orderItemNotFound) {
        super(orderItemNotFound);
    }
}
