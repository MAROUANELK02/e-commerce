package com.ecom.order_service.repositories;

import com.ecom.order_service.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
