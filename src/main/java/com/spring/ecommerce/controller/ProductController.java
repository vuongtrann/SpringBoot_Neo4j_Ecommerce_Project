package com.spring.ecommerce.controller;

import com.spring.ecommerce.persistence.model.Category;
import com.spring.ecommerce.persistence.model.Product;
import com.spring.ecommerce.persistence.repository.ProductReprository;
import com.spring.ecommerce.service.ProductService;
import com.spring.ecommerce.service.S3Service;
import com.spring.ecommerce.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v2")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private final S3Service s3Service;
    @Autowired
    private ProductReprository productReprository;

    public ProductController(S3Service s3Service, ProductService productService) {
        this.s3Service = s3Service;
        this.productService = productService;
    }

    /**Get all product*/
    @GetMapping("/products")
    public RestResponse getAllProducts() {
        return RestResponse.builder(productService.findAll()).message("Success").build();
    }

    /**Get product by id*/
    @GetMapping("/product/{productId}")
    public RestResponse getProductById(@PathVariable("productId") Long productId) {
        productService.viewCount(productId);
        return RestResponse.builder(productService.findById(productId)).message("Success").build();
    }

    /**Add product*/
    @PostMapping("category/{categoryId}/product")
    public RestResponse addProduct(@PathVariable("categoryId") Long categoryId ,@RequestBody Product product) {
        return RestResponse.builder(productService.save(product,categoryId)).message("Success").build();
    }

    /**Update product*/
    @PutMapping("product/{productId}")
    public RestResponse updateProduct(@PathVariable("productId") Long productId, @RequestBody Product product) {
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


    @PostMapping("product/{productId}/upload/image")
    public RestResponse uploadImage(@PathVariable("productId") Long productId, @RequestParam("file") MultipartFile[] files){
       List<File> fileList = new ArrayList<>();
       try {
           Product product = productService.findById(productId).get();
           for (MultipartFile file : files) {
               File localFile = File.createTempFile("image_",file.getOriginalFilename());
               file.transferTo(localFile);
               fileList.add(localFile);
           }
           List<String> fileURLs = s3Service.upload(productId,fileList);
           List<String> oldFileURLs = product.getImageURL();
           fileURLs.addAll(oldFileURLs);

           product.setImageURL(fileURLs);

           for (File file : fileList) {
               file.delete();
           }
           return RestResponse.builder(  productService.update(productId,product)).message("Success").build();

       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }


    @PostMapping("/upload")
    public RestResponse uploadImage1(@RequestParam("name") String name,@RequestParam("files") MultipartFile[] files,
                                    @RequestParam("description") String description,  @RequestParam("price") Double price,
                                    @RequestParam("rating") Double rating, @RequestParam("categoryId") Long id) {

        List<File> fileList = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                File localFile = File.createTempFile("temp",file.getOriginalFilename());
                file.transferTo(localFile);
                fileList.add(localFile);
            }

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setRating(rating);

            Product savedProduct = productService.save(product,id);
            List<String> fileUrls = s3Service.upload(savedProduct.getId(),fileList);

            savedProduct.setImageURL(fileUrls);
            productService.save(savedProduct,id);


            for (File file : fileList) {
                file.delete();
            }

            return RestResponse.builder(savedProduct).message("Success").build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("product/{productId}/delete/image")
    public RestResponse deleteImage(@PathVariable("productId") Long productId, @RequestBody List<String> url) {
        if (productService.findById(productId).isPresent()) {
            Product product = productService.findById(productId).get();
            List<String> imageURLs = product.getImageURL();
            imageURLs.removeAll(url);
//            s3Service.deleteImagesByUrls(url);
            product.setImageURL(imageURLs);

            return RestResponse.builder(productService.update(productId,product)).message("Success").build();
        }
        else {
            return RestResponse.builder().status(404).build();
        }

   }
}
