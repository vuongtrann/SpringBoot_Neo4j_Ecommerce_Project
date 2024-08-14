package com.spring.ecommerce.service;

import com.spring.ecommerce.persistence.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<Product> findAll();
    public Optional<Product> findById(Long id);
    public Product save(Product product);
    public void delete(Long id);
    public Product update(Long id, Product product);

}
