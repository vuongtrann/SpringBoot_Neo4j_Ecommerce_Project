package com.spring.ecommerce.service;

import com.spring.ecommerce.persistence.model.Evaluate;

import java.util.List;

public interface EvaluateService {
    public List<Evaluate> findAll();
    public Evaluate findById(Long id);
    public Evaluate save(Evaluate evaluate, Long productId, Long customerId);
    public void deleteById(Long id);
    public Evaluate update(Evaluate evaluate, Long id);
}
