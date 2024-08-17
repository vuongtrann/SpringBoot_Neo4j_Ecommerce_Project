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
        try {
            List<Category> categoryList = categoryService.getAllCategories();
            return RestResponse.builder(categoryList).message("Success").build();
        }catch (Exception e){
            return RestResponse.builder(null).message(e.getMessage()).build();
        }

    }

    /**Get category by id*/
    @GetMapping("/{categoryId}")
    public RestResponse getCategoryById(@PathVariable Long categoryId) {
        try {
            Optional<Category> category = categoryService.getCategoryById(categoryId);
            return RestResponse.builder(category).message("Success").build();
        }catch (Exception e) {
            return RestResponse.builder(null).message(e.getMessage()).build();
        }
    }

    /** Add category*/
    @PostMapping("")
    public RestResponse addCategory(@RequestBody Category category) {
        try {
            Category saveCategory = categoryService.save(category);
            return RestResponse.builder(saveCategory).message("Success").build();
        }catch (Exception e) {
            return RestResponse.builder(null).message(e.getMessage()).build();
        }

    }

    /** Add category with parent*/
    @PostMapping("/parent")
    public RestResponse addCategoryWithParent( @RequestBody Category category) {
        try {
            Category saveCategory = categoryService.saveWithParentID( category);
            return RestResponse.builder(saveCategory).message("Success").build();
        }catch (Exception e) {
            return RestResponse.builder(null).message(e.getMessage()).build();
        }

    }

    /** update category with parent*/
    @PutMapping ("/{categoryId}")
    public RestResponse updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        try {
            categoryService.update(categoryId, category);
            return RestResponse.builder(category).message("Success").build();
        }catch (Exception e) {
            return RestResponse.builder(null).message(e.getMessage()).build();
        }

    }

    @DeleteMapping("/{categoryId}")
    public RestResponse deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteById(categoryId);
            return RestResponse.builder("Deleted category by id : " + categoryId).message("Success").build();
        }catch (Exception e){
            return RestResponse.builder(null).message(e.getMessage()).build();
        }

    }

}
