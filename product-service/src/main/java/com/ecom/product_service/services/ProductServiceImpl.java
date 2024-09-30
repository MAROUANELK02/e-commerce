package com.ecom.product_service.services;

import com.ecom.product_service.entities.FileData;
import com.ecom.product_service.entities.Product;
import com.ecom.product_service.enums.ECategory;
import com.ecom.product_service.exceptions.ImageNotFoundException;
import com.ecom.product_service.exceptions.ProductNotFoundException;
import com.ecom.product_service.repositories.CategoryRepository;
import com.ecom.product_service.repositories.FileDataRepository;
import com.ecom.product_service.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;
    private final ImageService imageService;
    private final FileDataRepository fileDataRepository;

    @Override
    public Product addProduct(Product product) {
        Product save = productRepository.save(product);
        inventoryService.createInventory(save.getProductId());
        return save;
    }

    @Override
    public Page<Product> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getProduct(Long id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        try {
            Product product1 = getProduct(id);
            if(!product1.getName().equals(product.getName()))
                product1.setName(product.getName());
            if(!product1.getDescription().equals(product.getDescription()))
                product1.setDescription(product.getDescription());
            if(product.getCategory() != null &&
                    !product1.getCategory().getCategoryName().equals(product.getCategory()
                            .getCategoryName()))
                product1.setCategory(product.getCategory());
            if(!product1.getPrice().equals(product.getPrice()))
                product1.setPrice(product.getPrice());
            return productRepository.save(product1);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }else{
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public Page<Product> getProductsByCategory(ECategory category, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return productRepository.findByCategoryCategoryName(category,pageable);
    }

    @Override
    public Page<Product> getProductsByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return productRepository.findByNameContainsIgnoreCase(name,pageable);
    }

    @Override
    public Page<Product> getProductsByPriceLessThan(BigDecimal price, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return productRepository.findByPriceLessThanEqual(price,pageable);
    }

    @Override
    public Page<Product> getProductsByPriceGreaterThan(BigDecimal price, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return productRepository.findByPriceGreaterThanEqual(price,pageable);
    }

    @Override
    public void saveImageOfProduct(Long productId, MultipartFile image) throws ProductNotFoundException, IOException {
        Product product = getProduct(productId);
        imageService.uploadImageToStorage(product, image);
    }

    @Override
    public List<byte[]> getImagesOfProduct(Long productId) throws ProductNotFoundException {
        getProduct(productId);
        List<FileData> images = fileDataRepository.findAllByProductProductId(productId);
        List<byte[]> downloadedImages = new ArrayList<>();
        images.forEach(fileData -> {
            try {
                downloadedImages.add(
                        imageService.downloadImageFromStorage(fileData.getId())
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return downloadedImages;
    }

    @Override
    public byte[] getImageById(Long idImage) throws ImageNotFoundException, IOException {
        Optional<FileData> image = fileDataRepository.findById(idImage);
        if(image.isEmpty()) {
            throw new ImageNotFoundException("Image not found");
        }
        return imageService.downloadImageFromStorage(image.get().getId());
    }
}
