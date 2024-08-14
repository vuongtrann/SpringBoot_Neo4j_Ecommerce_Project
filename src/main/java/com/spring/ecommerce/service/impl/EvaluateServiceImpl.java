package com.spring.ecommerce.service.impl;

import com.spring.ecommerce.persistence.model.Customer;
import com.spring.ecommerce.persistence.model.Evaluate;
import com.spring.ecommerce.persistence.model.Product;
import com.spring.ecommerce.persistence.repository.CustomerRepository;
import com.spring.ecommerce.persistence.repository.EvaluateRepository;
import com.spring.ecommerce.service.EvaluateService;
import com.spring.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluateServiceImpl implements EvaluateService {
    @Autowired
    private EvaluateRepository evaluateRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    protected CustomerRepository customerRepository;

    @Override
    public List<Evaluate> findAll() {
        List<Evaluate> evaluateList = evaluateRepository.findAll();
        if(!evaluateList.isEmpty()){
            return evaluateList;
        }
        return null;
    }

    @Override
    public Evaluate findById(Long id) {
        Optional<Evaluate> evaluate = evaluateRepository.findById(id);
        if (evaluate.isPresent()) {
            return evaluate.get();
        }
        return null;
    }

    @Override
    public Evaluate save(Evaluate evaluate, Long productId, Long customerId) {
        Optional<Product> product = productService.findById(productId);
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (product.isPresent() && customer.isPresent()) {
            evaluate.setProduct(product.get());
            evaluate.setCustomer(customer.get());
            return evaluateRepository.save(evaluate);
        }
        else
            return null;


    }

    @Override
    public void deleteById(Long id) {
        evaluateRepository.deleteById(id);

    }

    @Override
    public Evaluate update(Evaluate evaluate, Long id) {
        Evaluate oldEvaluate = evaluateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not have Evaluate"));

        oldEvaluate.setPoints(evaluate.getPoints());
        oldEvaluate.setComment(evaluate.getComment());
        if (evaluate.getComment() != null) {
            oldEvaluate.setComment(evaluate.getComment());
        }
        if (evaluate.getProduct() != null) {
            oldEvaluate.setProduct(evaluate.getProduct());
        }

        return evaluateRepository.save(oldEvaluate);
    }
}
