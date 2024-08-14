package com.spring.ecommerce.service.impl;

import com.spring.ecommerce.persistence.model.Customer;
import com.spring.ecommerce.persistence.repository.CustomerRepository;
import com.spring.ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service


public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer customer, Long id) {
        Optional<Customer> oldCustomer = customerRepository.findById(id);
        if (oldCustomer.isPresent() && customer.getId().equals(oldCustomer.get().getId())) {
            oldCustomer.get().setName(customer.getName());
            oldCustomer.get().setEmail(customer.getEmail());
            oldCustomer.get().setPhone(customer.getPhone());
            return customerRepository.save(oldCustomer.get());
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        }
    }
}
