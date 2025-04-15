package com.ena.spring.finalex.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ena.spring.finalex.models.Customer;
import com.ena.spring.finalex.repository.CustomerRepository;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable String id) {
        return customerRepository.findById(id).orElse(null);
    }
    
    //checking here whether jackson is working.
    @GetMapping("/test")
    public Map<String, Object> testJackson() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Jackson is working!");
        response.put("time", LocalDateTime.now());
        return response;
    }

}