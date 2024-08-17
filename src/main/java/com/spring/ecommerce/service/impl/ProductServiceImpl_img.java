package com.spring.ecommerce.service.impl;

import com.spring.ecommerce.persistence.model.Category;
import com.spring.ecommerce.persistence.model.Evaluate;
import com.spring.ecommerce.persistence.model.Product;
import com.spring.ecommerce.persistence.model.Product_img;
import com.spring.ecommerce.persistence.repository.CategoryRepository;
import com.spring.ecommerce.persistence.repository.EvaluateRepository;
import com.spring.ecommerce.persistence.repository.ProductReprository;
import com.spring.ecommerce.persistence.repository.ProductReprository_img;
import com.spring.ecommerce.service.ProductService;
import com.spring.ecommerce.service.ProductService_img;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl_img implements ProductService_img {
    @Autowired
    private ProductReprository_img productReprository_img;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EvaluateRepository evaluateRepository;

    @Override
    public List<Product_img> findAll() {
        return productReprository_img.findAll();
    }

    @Override
    public Optional<Product_img> findById(Long id) {
        return productReprository_img.findById(id);
    }






    @Override
    public Product_img save(Product_img product, MultipartFile file) throws IOException {
        byte[] imgByte = file.getBytes();
        String imgUrl = Base64.getEncoder().encodeToString(imgByte);
        System.out.println(imgUrl);

//        if (product.getCategory().getId() != null) {
//            Category category = categoryRepository.findById(product.getCategory().getId())
//                    .orElseThrow(()-> new RuntimeException("Category not found"));
//            if(category != null) {
//                product.setCategory(category);
//            }
//        }
        return productReprository_img.save(product);
    }






    @Override
    public Product_img update(Long id, Product_img product) {
        Optional<Product_img> productOptional = productReprository_img.findById(id);
        if (productOptional.isPresent()) {
            Product_img existingProduct = productOptional.get();

            existingProduct.setName(product.getName());
            existingProduct.setImageURL(product.getImageURL());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setRatting(product.getRatting());
            existingProduct.setEvaluatting(product.getEvaluatting());

            if(product.getCategory().getId() != null) {
                Category category = categoryRepository.findById(product.getCategory().getId())
                        .orElseThrow(()-> new RuntimeException("Category not found"));
                if(category != null) {
                    existingProduct.setCategory(category);
                }
            }
            return productReprository_img.save(existingProduct);
        }else {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    public void delete(Long id) {
        productReprository_img.deleteById(id);
    }



    public Product_img updateRating( Long idProduct){
        Optional<Product_img> productOptional = productReprository_img.findById(idProduct);
        if (productOptional.isPresent()){
            Product_img existingProduct = productOptional.get();
            List<Evaluate> evaluates = evaluateRepository.findEvaluateByProductId(idProduct);

            try{
                double rating = evaluates.stream().mapToDouble( e -> e.getPoints())
                        .average().getAsDouble();
                existingProduct.setRatting(rating);
                return productReprository_img.save(existingProduct);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

        }else return null;
    }



}
