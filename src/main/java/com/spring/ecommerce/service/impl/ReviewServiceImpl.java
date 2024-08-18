package com.spring.ecommerce.service.impl;

import com.spring.ecommerce.persistence.model.Customer;
import com.spring.ecommerce.persistence.model.Review;
import com.spring.ecommerce.persistence.model.Product;
import com.spring.ecommerce.persistence.repository.CustomerRepository;
import com.spring.ecommerce.persistence.repository.ReviewRepository;
import com.spring.ecommerce.service.ReviewService;
import com.spring.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    protected CustomerRepository customerRepository;

    @Override
    public List<Review> findAll() {
        List<Review> reviewList = reviewRepository.findAll();
        if(!reviewList.isEmpty()){
            return reviewList;
        }
        return null;
    }

    @Override
    public Review findById(Long id) {
        Optional<Review> evaluate = reviewRepository.findById(id);
        if (evaluate.isPresent()) {
            return evaluate.get();
        }
        return null;
    }

    @Override
    public Review save(Review review, Long productId) {

        Optional<Product> product = productService.findById(productId);
        Optional<Customer> customer = customerRepository.findById(review.getCustomerId());
        if (product.isPresent() && customer.isPresent()) {
            review.setProduct(product.get());
            review.setCustomer(customer.get());
            reviewRepository.save(review);
            productService.updateRating(productId);
            return review;
        }
        else
            return null;
    }

    @Override
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);

    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }


    @Override
    public Review update(Review form, Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not have Evaluate"));

        review.setRating(form.getRating());
        review.setComment(form.getComment());
        if (form.getComment() != null) {
            review.setComment(form.getComment());
        }

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> findByProductId(Long productId) {
        try{
            return reviewRepository.findEvaluateByProductId(productId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Review> findByCustomerId(Long customerId) {
        return List.of();
    }
}
