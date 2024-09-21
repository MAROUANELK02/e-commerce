package com.ecom.order_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private Long productId;
    private int quantity;
    private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Order order;
}
