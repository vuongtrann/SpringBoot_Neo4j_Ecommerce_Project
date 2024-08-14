package com.spring.ecommerce.controller;

import com.spring.ecommerce.persistence.model.Product;
import com.spring.ecommerce.service.ProductService;
import com.spring.ecommerce.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**Get all product*/
    @GetMapping("")
    public RestResponse getAllProducts() {
        return RestResponse.builder(productService.findAll()).message("Success").build();
    }

    /**Get product by id*/
    @GetMapping("/{productId}")
    public RestResponse getProductById(@PathVariable("productId") Long productId) {
        return RestResponse.builder(productService.findById(productId)).message("Success").build();
    }

    /**Add product*/
    @PostMapping("")
    public RestResponse addProduct(@RequestBody Product product) {
        return RestResponse.builder(productService.save(product)).message("Success").build();
    }

    /**Update product*/
    @PutMapping("/{productId}")
    public RestResponse updateProduct(@PathVariable("productId") Long productId, @RequestBody Product product) {
        return RestResponse.builder(productService.update(productId, product)).message("Success").build();
    }

    /**Delete product by id*/
    @DeleteMapping("/{productId}")
    public RestResponse deleteProductById(@PathVariable("productId") Long productId) {
        productService.delete(productId);
        return RestResponse.builder("Deleted product by id : " +productId).message("Success").build();
    }
}
