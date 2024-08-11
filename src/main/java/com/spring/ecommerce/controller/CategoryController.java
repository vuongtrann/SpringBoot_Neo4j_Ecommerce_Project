package com.spring.ecommerce.controller;


import com.spring.ecommerce.persistence.model.Category;
import com.spring.ecommerce.service.CategoryService;
import com.spring.ecommerce.service.impl.CategoryServiceImpl;
import com.spring.ecommerce.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**Get all category*/
    @GetMapping("")
    public RestResponse getAllCategories() {
        List<Category> categoryList = categoryService.getAllCategories();
        return RestResponse.builder(categoryList).message("Success").build();
    }

    /**Get category by id*/
    @GetMapping("/{categoryId}")
    public RestResponse getCategoryById(@PathVariable Long categoryId) {
        Optional<Category> category = categoryService.getCategoryById(categoryId);
        return RestResponse.builder(category).message("Success").build();
    }

    /** Add category*/
    @PostMapping("")
    public RestResponse addCategory(@RequestBody Category category) {
        Category saveCategory = categoryService.save(category);
        return RestResponse.builder(saveCategory).message("Success").build();
    }

    /** Add category with parent*/
    @PostMapping("/parent")
    public RestResponse addCategoryWithParent( @RequestBody Category category) {
        Category saveCategory = categoryService.saveWithParentID( category);
        return RestResponse.builder(saveCategory).message("Success").build();
    }

    /** update category with parent*/
    @PutMapping ("/{categoryId}")
    public RestResponse updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        categoryService.update(categoryId, category);
        return RestResponse.builder(category).message("Success").build();
    }

    @DeleteMapping("/{categoryId}")
    public RestResponse deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteById(categoryId);
        return RestResponse.builder("Deleted category by id : " + categoryId).message("Success").build();
    }

}
