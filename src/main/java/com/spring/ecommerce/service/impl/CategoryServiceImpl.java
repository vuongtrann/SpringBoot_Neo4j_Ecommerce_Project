package com.spring.ecommerce.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ecommerce.persistence.model.Category;
import com.spring.ecommerce.persistence.model.Product;
import com.spring.ecommerce.persistence.repository.CategoryRepository;
import com.spring.ecommerce.persistence.repository.ProductReprository;
import com.spring.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class CategoryServiceImpl implements CategoryService {

    ObjectMapper mapper = new ObjectMapper();//.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    private CategoryRepository categoryRepository;
    private ProductReprository productReprository;


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return Optional.empty();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category saveWithParentID(Category category) {
        Long parentID = category.getParentID();
        if (parentID != null) {
            Category parent = categoryRepository.findById(parentID)
                    .orElseThrow(()-> new RuntimeException("Parent category not found"));
            category.setParent(parent);
        }
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> saveAll(List<Category> categories) {
        return List.of();
    }


    @Override
    public Category update(Long categoryId, Category category) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        if (category.getName() != null) {
            existingCategory.setName(category.getName());
            if (category.getParentID() != null) {
            Category parentCategory = categoryRepository.findById(category.getParentID())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            existingCategory.setParent(parentCategory);
            }
        }
        return categoryRepository.save(existingCategory);
    }
    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Product addProduct(Long categoryId, Product newProduct) {
        return null;
    }


    @Override
    public List<Category> getTopCategory(int limit) throws NullPointerException {
        List<Map<String, Object>> list = categoryRepository.getTopCategory(limit);
        List<Category> categories = list.stream().map((m) -> {
            Category category = mapper.convertValue(m, Category.class);
            return category;
        }).toList();
        return categories;
    };




}
