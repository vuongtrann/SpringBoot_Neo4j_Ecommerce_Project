package com.spring.ecommerce.service;

import com.spring.ecommerce.persistence.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface CustomerService {
    public Optional<Customer> findById(Long id);
    public List<Customer> findAll();
    public Customer save(Customer customer);
    public Customer update(Customer customer, Long id);
    public void delete(Long id);

}
