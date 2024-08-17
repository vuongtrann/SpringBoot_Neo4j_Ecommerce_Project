package com.spring.ecommerce.controller;

import com.spring.ecommerce.persistence.model.Product;
import com.spring.ecommerce.service.ProductService;
import com.spring.ecommerce.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//        try{
//
//        }
        return RestResponse.builder(productService.update(productId, product)).message("Success").build();
    }

//    @DeleteMapping("/{productId}")
//    public ResponseEntity <String> deleteProduct(@PathVariable Long productId){
//        try {
//            if (productService.findById(productId).isPresent()){
//                productService.delete(productId);
//                return ResponseEntity.status(HttpStatus.OK).body("Deleted product by id : " + productId);
//            }else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id : " + productId + " not found");
//            }
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//
//    }


    /**Delete product by id*/
    @DeleteMapping("/{productId}")
    public RestResponse deleteProductById(@PathVariable("productId") Long productId) {
        try {
            if (productService.findById(productId).isPresent()) {
                productService.delete(productId);
                return RestResponse.builder("Deleted product by id : " + productId).message("Success").build();
            }else {
                return RestResponse.builder("Product with id " + productId + " not found !").message("NOT FOUND").status(404).build();
            }
        }catch (Exception e){
            return RestResponse.builder(null).message(e.getMessage()).build();
        }
    }
}
