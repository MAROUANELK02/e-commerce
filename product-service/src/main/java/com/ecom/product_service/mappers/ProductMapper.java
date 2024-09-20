package com.ecom.product_service.mappers;

import com.ecom.product_service.dtos.ProductDTO;
import com.ecom.product_service.entities.Category;
import com.ecom.product_service.entities.Product;
import com.ecom.product_service.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductMapper {
    private final CategoryRepository categoryRepository;

    public Product ToProduct(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        Optional<Category> category = categoryRepository
                .findByCategoryName(productDTO.getProductCategory());
        product.setCategory(category.orElse(null));
        return product;
    }

    public ProductDTO ToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        productDTO.setProductCategory(product.getCategory().getCategoryName());
        return productDTO;
    }
}
