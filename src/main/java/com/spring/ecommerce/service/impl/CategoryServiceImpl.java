package com.spring.ecommerce.service.impl;

import com.spring.ecommerce.persistence.model.Category;
import com.spring.ecommerce.persistence.model.Product;
import com.spring.ecommerce.persistence.repository.CategoryRepository;
import com.spring.ecommerce.persistence.repository.ProductReprository;
import com.spring.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryServiceImpl implements CategoryService {

     @Autowired
    private CategoryRepository categoryRepository;
     @Autowired
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

//    @Override
//    public Category update(Long catId, Category category) {
//       Category existCat = categoryRepository.findById(catId)
//               .orElseThrow(()-> new RuntimeException("Category not found"));
//       if (existCat.getParent() != null && !category.getName().isEmpty()) {
//           existCat.setName(category.getName());
//       }
//       if (category.getParent() != null && category.getParent().getId() != null) {
//           Category parent = categoryRepository.findById(category.getParent().getId())
//                   .orElseThrow(()-> new RuntimeException("Parent category not found"));
//           existCat.setParent(parent);
//       }else {
//           existCat.setParent(null);
//       }
//       return categoryRepository.save(existCat);
//    }


    @Override
    public Category update(Long categoryId, Category category) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (category.getName() != null && !category.getName().isEmpty()) {
            existingCategory.setName(category.getName());

            //Kiem tra co ton tai parent khong
            if (category.getParentID() != null) {
            Category parentCategory = categoryRepository.findById(category.getParentID())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            existingCategory.setParent(parentCategory);
            }else { //neu parent == null -> remove relationship
                existingCategory.setParent(null);
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
}
