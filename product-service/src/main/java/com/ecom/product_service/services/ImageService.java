package com.ecom.product_service.services;

import com.ecom.product_service.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    byte[] downloadImageFromStorage(Long id) throws IOException;
    void uploadImageToStorage(Product product, MultipartFile file) throws IOException;
}
