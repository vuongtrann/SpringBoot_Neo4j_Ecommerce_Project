package com.spring.ecommerce.controller;

import com.spring.ecommerce.persistence.dto.ProductForm;
import com.spring.ecommerce.persistence.model.Category;
import com.spring.ecommerce.persistence.model.Product;
import com.spring.ecommerce.persistence.repository.ProductReprository;
import com.spring.ecommerce.service.CategoryService;
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
import java.util.*;

@RestController
@RequestMapping("/api/v2/products")
public class ProductController {
    private static final int MAX_LENGTH_NAME_PRODUCT = 120;
    private static final int MIN_LENGTH_NAME_PRODUCT = 5;
    private static final int MAX_LENGTH_DESCRIPTION_PRODUCT = 1000;
    private static final int MIN_LENGTH_DESCRIPTION_PRODUCT = 200;

    @Autowired
    private ProductService productService;
    @Autowired
    private final S3Service s3Service;

    @Autowired
    private CategoryService categoryService;

    public ProductController(S3Service s3Service, ProductService productService) {
        this.s3Service = s3Service;
        this.productService = productService;
    }

    /** CREATE */

    /**Add product*/
    @PostMapping("")
    public ResponseEntity addProduct(@RequestBody ProductForm form)  {
        if (form.getName().length() > MIN_LENGTH_NAME_PRODUCT && form.getName().length() < MAX_LENGTH_NAME_PRODUCT ){
            if (form.getDescription().length() > MIN_LENGTH_DESCRIPTION_PRODUCT && form.getDescription().length() < MAX_LENGTH_DESCRIPTION_PRODUCT){
                Product product = productService.add(form);
                return new ResponseEntity<>(product,HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>("Description length should be greater than 200 characters and less than 1000 characters.", HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity<>("Product name length should be greater than 5 characters and less than 120 characters.",HttpStatus.BAD_REQUEST);

        }
    }

    /**Upload Image*/
    @PostMapping("/{productId}/upload/image")
    public ResponseEntity uploadImage(@PathVariable("productId") Long productId, @RequestParam("file") MultipartFile[] files){
        List<File> fileList = new ArrayList<>();
        try {
            Product product = productService.findById(productId).orElseThrow(()->new RuntimeException("Product with id : " + productId + " not found"));
            for (MultipartFile file : files) {
                File localFile = File.createTempFile("image_",file.getOriginalFilename());
                file.transferTo(localFile);
                fileList.add(localFile);
            }
            List<String> fileURLs = s3Service.upload(productId,fileList);
            List<String> oldFileURLs = product.getImageURL();
            if (oldFileURLs != null) {
                fileURLs.addAll(oldFileURLs);
                product.setImageURL(fileURLs);
                product.setPrimaryImageURL(fileURLs.get(0));
            }else {
                product.setImageURL(fileURLs);
                product.setPrimaryImageURL(fileURLs.get(0));
            }

            for (File file : fileList) {
                file.delete();
            }
            return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** READ */

    /**Get all product*/
//    @GetMapping("")
//    public ResponseEntity<List<Product>> getAllProducts() {
//        if (productService.findAll().isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }else{
//            return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
//        }
//    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(name = "orderByCreatedAt",defaultValue = "ASC")String orderBy){
        return new ResponseEntity<>(productService.findAllProduct(orderBy),HttpStatus.OK);
    }

    /**Get product by id*/
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId) {
        if (!productService.findById(productId).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(productService.findById(productId).get(), HttpStatus.OK);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> getProductByName(@RequestParam("name") String name) {
        List<Product> products = productService.findByNameProduct(name);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(products,HttpStatus.OK);
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getAllProductByCategory(@PathVariable Long categoryId){
        List<Product> products = productService.getAllProductsByCategoryId(categoryId);
        if (products.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }


    /** UPDATE */

    /**Update product*/
    @PutMapping("/{productId}")
    public ResponseEntity updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductForm form) {
        if (form.getName().length() > MIN_LENGTH_NAME_PRODUCT && form.getName().length() < MAX_LENGTH_NAME_PRODUCT ){
            if (form.getDescription().length() > MIN_LENGTH_DESCRIPTION_PRODUCT && form.getDescription().length() < MAX_LENGTH_DESCRIPTION_PRODUCT){
                Product newProduct = productService.update(form,productId);
                return new ResponseEntity<>(newProduct,HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>("Description length should be greater than 200 characters and less than 1000 characters.", HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity<>("Product name length should be greater than 5 characters and less than 120 characters.",HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping ("/sold/{id}/product")
    public RestResponse soldProduct(@PathVariable("id") Long id) throws Exception {
        productService.soldOut(id);
        return RestResponse.builder().message("Success").build();
    }

    /** DELETE */

    @DeleteMapping("/{productId}")
    public ResponseEntity <String> deleteProduct(@PathVariable Long productId){
        if (productService.findById(productId).isPresent()){
            productService.delete(productId);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted product by id : " + productId);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id : " + productId + " not found");
        }
    }


    @DeleteMapping("/{productId}/delete/image")
    public ResponseEntity deleteImage(@PathVariable("productId") Long productId, @RequestBody List<String> url) {
        if (productService.findById(productId).isPresent()) {
            Product product = productService.findById(productId).get();
            List<String> imageURLs = product.getImageURL();
            imageURLs.removeAll(url);
            s3Service.deleteImagesByUrls(url);
            product.setImageURL(imageURLs);

            return new ResponseEntity<>(productService.save(product),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Product with id : " + productId + " not found", HttpStatus.NOT_FOUND);
        }

    }





//    /**Delete product by id*/
//    @DeleteMapping("/product/{productId}")
//    public RestResponse deleteProductById(@PathVariable("productId") Long productId) {
//        try {
//            if (productService.findById(productId).isPresent()) {
//                productService.delete(productId);
//                return RestResponse.builder("Deleted product by id : " + productId).message("Success").build();
//            }else {
//                return RestResponse.builder("Product with id " + productId + " not found !").message("NOT FOUND").status(404).build();
//            }
//        }catch (Exception e){
//            return RestResponse.builder(null).message(e.getMessage()).build();
//        }
//    }





//    @PostMapping("/upload")
//    public RestResponse uploadImage1(@RequestParam("name") String name,@RequestParam("files") MultipartFile[] files,
//                                    @RequestParam("description") String description,  @RequestParam("price") Double price,
//                                    @RequestParam("rating") Double rating, @RequestParam("categoryId") Long id) {
//
//        List<File> fileList = new ArrayList<>();
//        try {
//            for (MultipartFile file : files) {
//                File localFile = File.createTempFile("temp",file.getOriginalFilename());
//                file.transferTo(localFile);
//                fileList.add(localFile);
//            }
//
//            Product product = new Product();
//            product.setName(name);
//            product.setDescription(description);
//            product.setPrice(price);
//            product.setRating(rating);
//
//            Product savedProduct = productService.save(product,id);
//            List<String> fileUrls = s3Service.upload(savedProduct.getId(),fileList);
//
//            savedProduct.setImageURL(fileUrls);
//            productService.save(savedProduct,id);
//
//
//            for (File file : fileList) {
//                file.delete();
//            }
//
//            return RestResponse.builder(savedProduct).message("Success").build();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
