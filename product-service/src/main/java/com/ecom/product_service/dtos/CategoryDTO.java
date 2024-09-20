package com.ecom.product_service.dtos;

import com.ecom.product_service.enums.ECategory;
import lombok.*;

@Data
public class CategoryDTO {
    private Long categoryId;
    private ECategory categoryName;
}
