package com.spring.ecommerce.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ecommerce.persistence.dto.CategoryForm;
import com.spring.ecommerce.persistence.dto.ProductForm;
import com.spring.ecommerce.persistence.model.Category;
import com.spring.ecommerce.persistence.model.Product;
import com.spring.ecommerce.persistence.repository.CategoryRepository;
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



    @Override
    public List<Category> getAllCategories()throws NullPointerException {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> getCategoryByName(String name)throws NullPointerException {
        return Optional.empty();
    }

    @Override
    public Category save(Category category) throws NullPointerException {
        return categoryRepository.save(category);
    }

//    @Override
//    public Category addParent(CategoryForm form) {
//        List<Category> items = form.getCategories().stream()
//                .map(item -> {
//                    Optional<Category> categoryOptional = categoryRepository.findById(item);
//                    if (categoryOptional.isPresent()) {
//                        Category category = categoryOptional.get();
//                        return category;
//                    }
//                    return null;
//                }).toList();
//        Category category = new Category(form.getName(),items);
//        category = save(category);
//        return category;
//    }

    @Override
    public Category addParent(Category category) {
        Long parentID = category.getParentID();
        if (parentID != null) {
            Category parent = categoryRepository.findById(parentID)
                    .orElseThrow(()-> new RuntimeException("Parent category not found"));
            category.setCategories(parent);
        }
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> saveAll(List<Category> categories) {
        return List.of();
    }

    @Override
    public Category update(Long catId, Category category) {
        return null;
    }


    //    @Override
//    public Category update(Long categoryId, Category category) {
//        Category existingCategory = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new RuntimeException("Category not found"));
//        if (category.getName() != null) {
//            existingCategory.setName(category.getName());
//            if (category.getParentID() != null) {
//            Category parentCategory = categoryRepository.findById(category.getParentID())
//                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
//            existingCategory.setParent(parentCategory);
//            }
//        }
//        return categoryRepository.save(existingCategory);
//    }
    @Override
    public void deleteById(Long id)  {
        if (id == null) {
            throw new RuntimeException("Id is null");
        }
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
