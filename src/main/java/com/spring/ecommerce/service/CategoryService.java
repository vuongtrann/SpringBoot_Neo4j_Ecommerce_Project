package com.spring.ecommerce.service;

import com.spring.ecommerce.persistence.dto.CategoryForm;
import com.spring.ecommerce.persistence.dto.ProductForm;
import com.spring.ecommerce.persistence.model.Category;
import com.spring.ecommerce.persistence.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    public List<Category> getAllCategories();
    public Optional<Category> findById(Long id);
    public Optional<Category> getCategoryByName(String name);
    public Category save(Category category);
    public Category addParent(Category category);


    public List<Category> saveAll(List<Category> categories);
    public Category update(Long catId ,Category category);
    public void deleteById(Long id);
    public Product addProduct(Long categoryId, Product newProduct);


    public List<Category> getTopCategory(int limit);
}
