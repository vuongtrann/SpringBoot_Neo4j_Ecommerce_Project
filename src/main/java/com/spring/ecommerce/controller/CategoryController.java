package com.spring.ecommerce.controller;


import com.spring.ecommerce.persistence.dto.CategoryForm;
import com.spring.ecommerce.persistence.model.Category;
import com.spring.ecommerce.persistence.model.Product;
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
@CrossOrigin(origins = "https://minhhieu212.github.io/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**Get all category*/
    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categoryList = categoryService.getAllCategories();
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/search")
    public ResponseEntity<List<Category>> getAllCategoriesByName(@RequestParam("name") String name) throws Exception{
        try {
//            return RestResponse.builder(categoryService.findByCategoryName(name)).message("Success").build();
            return new  ResponseEntity<>(categoryService.findByCategoryName(name), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Category>> getAllCategoryByProductId(@PathVariable Long productId){
        List<Category> categories = categoryService.getAllCategoryByProductId(productId);
        if (categories.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(categories,HttpStatus.OK);
        }
    }

    /**Get category by id*/
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
        try {
            Optional<Category> category = categoryService.findById(categoryId);
            if (category.isPresent()){
                return new ResponseEntity<>(category.get(), HttpStatus.OK);
            }
            else   return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** Add category*/
    @PostMapping("")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        try {
            return new ResponseEntity<>(categoryService.save(category), HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /** Add category with parent*/
    @PostMapping("/parent")
    public ResponseEntity<Category> addCategoryWithParent( @RequestBody CategoryForm form) {
        try {
            return new ResponseEntity<>(categoryService.addParent(form), HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /** update category with parent*/
    @PutMapping ("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        try {
            return new ResponseEntity<>(categoryService.update(categoryId, category), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteById(categoryId);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/top")
    public ResponseEntity<List<Category>> getTopCategory(@RequestParam(defaultValue = "10") int limit) throws NullPointerException {
        try {
            return new ResponseEntity<>(categoryService.getTopCategory(limit), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
