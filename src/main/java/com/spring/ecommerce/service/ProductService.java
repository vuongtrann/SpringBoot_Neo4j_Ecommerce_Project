package com.spring.ecommerce.service;

import com.spring.ecommerce.persistence.dto.ProductForm;
import com.spring.ecommerce.persistence.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductService {
    public List<Product> findAll();
    public Optional<Product> findById(Long id);
    public List<Product> findByNameProduct(String name);
    public Product save(Product product);
    public void delete(Long id);
    public Product add(ProductForm form);
    public Product update(ProductForm form, Long productId);

    public Product updateRating(Long productId);

    public void viewCount(Product product);
    public void soldOut(Long productId);
}
