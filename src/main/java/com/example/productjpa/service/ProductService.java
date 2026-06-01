package com.example.productjpa.service;

import com.example.productjpa.entity.Product;
import com.example.productjpa.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private static final String UPLOAD_DIR = "uploads/";

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    public void addProduct(Product product, MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImagename(uploadImage(imageFile));
        }
        productRepository.save(product);
    }

    public void updateProduct(Product product, MultipartFile imageFile) {
        Product existing = getById(product.getId());
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setStock(product.getStock());
        existing.setDetails(product.getDetails());

        if (imageFile != null && !imageFile.isEmpty()) {
            if (existing.getImagename() != null) {
                deleteImage(existing.getImagename());
            }
            existing.setImagename(uploadImage(imageFile));
        }

        productRepository.save(existing);
    }

    public void deleteById(Long id) {
        Product product = getById(id);
        if (product.getImagename() != null) {
            deleteImage(product.getImagename());
        }
        productRepository.deleteById(id);
    }

    private String uploadImage(MultipartFile file) {
        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    private void deleteImage(String imagename) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + imagename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image", e);
        }
    }
}
