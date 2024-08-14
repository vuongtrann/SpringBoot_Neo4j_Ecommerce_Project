package com.spring.ecommerce.controller;


import com.spring.ecommerce.persistence.model.Customer;
import com.spring.ecommerce.persistence.repository.CustomerRepository;
import com.spring.ecommerce.service.CustomerService;
import com.spring.ecommerce.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v2/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;


    @GetMapping("")
    public RestResponse getAllCustomers() {
        try {
            List<Customer> listCustomer = customerService.findAll();
            return RestResponse.builder(listCustomer).message("Success").build();
        } catch (Exception e) {
            return RestResponse.builder(null).message("Error: " +e.getMessage()).build();
        }
    }


    @GetMapping("/{id}")
    public RestResponse getCustomer(@PathVariable Long id) {
        try {
            Customer customer = customerService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            return RestResponse.builder(customer).message("Success").build();
        }catch (Exception e) {
                return RestResponse.builder(null).message("Error: " +e.getMessage()).build();
            }
    }


    @PostMapping("/add")
    public RestResponse addCustomer(@RequestBody Customer customer) {
        try {
            customerService.save(customer);
            return RestResponse.builder(customer).message("Success").build();
        }catch (Exception e) {
            return RestResponse.builder(null).message("Error: " +e.getMessage()).build();
        }
    }


    @PutMapping("/{id}/update")
    public RestResponse updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        try {
            customerService.update(customer, id);
            return RestResponse.builder(customer).message("Success").build();
        }catch (Exception e) {
            return RestResponse.builder(customer).message("Error" + e.getMessage()).build();
        }
    }


    @DeleteMapping("/{id}/delete")
    public RestResponse deleteCustomer(@PathVariable Long id) {
        try{
        customerService.delete(id);
        return RestResponse.builder().message("Success").build();
        }catch (Exception e) {
            return RestResponse.builder(null).message("Error: " +e.getMessage()).build();
        }
    }



}
