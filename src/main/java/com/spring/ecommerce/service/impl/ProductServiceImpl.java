package com.spring.ecommerce.service.impl;

import com.spring.ecommerce.persistence.dto.ProductForm;
import com.spring.ecommerce.persistence.model.Category;
import com.spring.ecommerce.persistence.model.Review;
import com.spring.ecommerce.persistence.model.Product;
import com.spring.ecommerce.persistence.repository.CategoryRepository;
import com.spring.ecommerce.persistence.repository.ReviewRepository;
import com.spring.ecommerce.persistence.repository.ProductReprository;
import com.spring.ecommerce.service.CategoryService;
import com.spring.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductReprository productReprository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ReviewRepository evaluateRepository;

    @Autowired
    CategoryService categoryService;


    @Override
    public List<Product> findAll() {
        return productReprository.findAll();
    }

    @Override
    public List<Product> findAllProduct(String orderBy) {
        if (orderBy.equals("DESC")){
            return productReprository.getAllProductDESC();
        }else{
            return productReprository.getAllProductASC();
        }

    }


    @Override
    public Optional<Product> findById(Long id) throws NullPointerException {
        Optional<Product> product = productReprository.findById(id);
        if (product.isPresent()) {
            viewCount(product.get());
            return product;
        }
        return null;
    }

    @Override
    public List<Product> findByNameProduct(String name) {
        return productReprository.findByNameProduct(name);
    }

    @Override
    public List<Product> getAllProductsByCategoryId(Long categoryId) {
        return productReprository.getAllProductByCategoryId(categoryId);
    }


    @Override
    public Product save(Product product) {
      return productReprository.save(product);
    }

    @Override
    public Product add(ProductForm form) {
        List<Category> items = form.getCategories().stream()
                .map( item -> {
                    Optional<Category> opt = categoryService.findById(item);
                    if (opt.isPresent()) {
                        Category category = opt.get();
                        return category;
                    }
                    return null;
                }).toList();

        Product product = new Product(form.getName(), form.getDescription(),form.getPrice(),items);
        product.setCreatedAt(LocalDateTime.now());
        product = save(product);
        return product;
    }

    @Override
    public Product update(ProductForm form, Long productId){
        Product existingProduct = productReprository.findById(productId).orElseThrow(()-> new RuntimeException("Product not found"));
        List<Category> categories = form.getCategories().stream()
                .map( item-> {
                    Optional<Category> opt = categoryService.findById(item);
                    if (opt.isPresent()) {
                        Category category = opt.get();
                        return category;
                    }
                    return null;
                }).toList();

        existingProduct.setName(form.getName());
        existingProduct.setImageURL(form.getImageURL());
        existingProduct.setDescription(form.getDescription());
        existingProduct.setPrice(form.getPrice());
        existingProduct.setOldPrice(form.getOldPrice());
        existingProduct.setCategories(categories);
        existingProduct.setUpdatedAt(LocalDateTime.now());
        return save(existingProduct);
    }


    @Override
    public void delete(Long id) {
        productReprository.deleteById(id);
    }



    public Product updateRating( Long idProduct){
        Optional<Product> productOptional = productReprository.findById(idProduct);
        if (productOptional.isPresent()){
            Product existingProduct = productOptional.get();
            List<Review> reviews = evaluateRepository.findEvaluateByProductId(idProduct);

            try{
                double rating = reviews.stream().mapToDouble(e -> e.getRating())
                        .average().getAsDouble();
                existingProduct.setRating(rating);
                return productReprository.save(existingProduct);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

        }else return null;
    }

    @Override
    public void viewCount(Product product) throws NullPointerException {
        int count = product.getViewCount()+1;
        product.setViewCount(count);
        productReprository.save(product);
    }

    @Override
    public void soldOut(Long productId) throws NullPointerException {
        Optional<Product> productOptional = productReprository.findById(productId);
        if (productOptional.isPresent()){
            Product product = productOptional.get();
            product.setQuantitySold(product.getQuantitySold()+1);
            product.setRemainingQuantity(product.getRemainingQuantity()-1);
            productReprository.save(product);
        }
    };



}
