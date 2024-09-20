package com.ecom.product_service.dtos;

import com.ecom.product_service.enums.ECategory;
import jakarta.validation.constraints.Digits;
import lombok.*;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long productId;
    private String name;
    private String description;
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;
    private ECategory productCategory;
}