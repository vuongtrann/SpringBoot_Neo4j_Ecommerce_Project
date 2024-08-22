package com.spring.ecommerce.service.impl;

import com.spring.ecommerce.persistence.model.Category;
import com.spring.ecommerce.persistence.model.Review;
import com.spring.ecommerce.persistence.model.Product;
import com.spring.ecommerce.persistence.repository.CategoryRepository;
import com.spring.ecommerce.persistence.repository.ReviewRepository;
import com.spring.ecommerce.persistence.repository.ProductReprository;
import com.spring.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductReprository productReprository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ReviewRepository evaluateRepository;

    @Override
    public List<Product> findAll() {
        return productReprository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productReprository.findById(id);
    }

    @Override
    public Product save(Product product, Long categoryId) {
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(()-> new RuntimeException("Category not found"));
            if(category != null) {
                product.setCategory(category);
            }
        }
        return productReprository.save(product);
    }

    @Override
    public Product update(Long id, Product product) {
        Optional<Product> productOptional = productReprository.findById(id);
        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();

            existingProduct.setName(product.getName());
            existingProduct.setImageURL(product.getImageURL());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());

            if(product.getCategory().getId() != null) {
                Category category = categoryRepository.findById(product.getCategory().getId())
                        .orElseThrow(()-> new RuntimeException("Category not found"));
                if(category != null) {
                    existingProduct.setCategory(category);
                }
            }
            return productReprository.save(existingProduct);
        }else {
            throw new RuntimeException("Product not found");
        }
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



}
