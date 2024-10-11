package com.ecom.product_service.repositories;

import com.ecom.product_service.entities.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData, Long> {
    List<FileData> findAllByProductProductId(Long productId);
    Optional<FileData> findByProductProductId(Long productId);
}
