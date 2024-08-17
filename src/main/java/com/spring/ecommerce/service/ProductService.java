package com.spring.ecommerce.service;

import com.spring.ecommerce.persistence.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<Product> findAll();
    public Optional<Product> findById(Long id);
    public Product save(Product product, MultipartFile file) throws IOException;
    public void delete(Long id);
    public Product update(Long id, Product product);

    public Product updateRating(Long productId);
}
