package com.spring.ecommerce.service;

import com.spring.ecommerce.persistence.model.Product;
import com.spring.ecommerce.persistence.model.Product_img;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService_img {
    public List<Product_img> findAll();
    public Optional<Product_img> findById(Long id);
    public Product_img save(Product_img product, MultipartFile file) throws IOException;
    public void delete(Long id);
    public Product_img update(Long id, Product_img product);

    public Product_img updateRating(Long productId);
}
