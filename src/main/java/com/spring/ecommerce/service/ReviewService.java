package com.spring.ecommerce.service;

import com.spring.ecommerce.persistence.model.Review;

import java.util.List;

public interface ReviewService {
    public List<Review> findAll();
    public Review findById(Long id);
    public Review save(Review review, Long productId);
    public void deleteById(Long id);
    public Review save(Review review);
    public Review update(Review review, Long id);

    public List<Review> findByProductId(Long productId);
    public List<Review> findByCustomerId(Long customerId);

}
