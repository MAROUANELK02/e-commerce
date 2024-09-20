package com.ecom.product_service.repositories;

import com.ecom.product_service.entities.Category;
import com.ecom.product_service.enums.ECategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryName(ECategory name);
}
