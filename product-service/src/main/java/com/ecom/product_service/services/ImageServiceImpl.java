package com.ecom.product_service.services;

import com.ecom.product_service.entities.FileData;
import com.ecom.product_service.entities.Product;
import com.ecom.product_service.repositories.FileDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final FileDataRepository fileDataRepository;

    @Value("${images.path}")
    private String storagePath;

    public ImageServiceImpl(FileDataRepository fileDataRepository) {
        this.fileDataRepository = fileDataRepository;
    }

    @Override
    public byte[] downloadImageFromStorage(Long id) throws IOException {
        log.info("Downloading image from storage: ");
        Optional<FileData> fileData = fileDataRepository.findById(id);
        if (fileData.isEmpty()) {
            log.error("File not found: ");
            return new byte[0];
        }
        String fileName = fileData.get().getName();
        return Files.readAllBytes(new File(storagePath + File.separator + fileName).toPath());
    }

    @Override
    public void uploadImageToStorage(Product product, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + fileExtension;

        File newFile = new File(storagePath + File.separator + newFileName);

        file.transferTo(newFile);

        fileDataRepository.save(FileData.builder()
                .name(newFileName)
                .type(file.getContentType())
                .product(product)
                .build());
    }
}
