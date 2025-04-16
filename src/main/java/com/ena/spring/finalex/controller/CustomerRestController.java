package com.ena.spring.finalex.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ena.spring.finalex.models.Customer;
import com.ena.spring.finalex.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public String createCustomer(@RequestBody String json) throws JsonProcessingException {
        Customer customer = objectMapper.readValue(json, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return objectMapper.writeValueAsString(savedCustomer);
    }

    @GetMapping
    public String getAllCustomers() throws JsonProcessingException {
        List<Customer> customers = customerRepository.findAll();
        return objectMapper.writeValueAsString(customers);
    }

    @GetMapping("/{id}")
    public String getCustomer(@PathVariable String id) throws JsonProcessingException {
        return customerRepository.findById(id)
                .map(customer -> {
                    try {
                        return objectMapper.writeValueAsString(customer);
                    } catch (JsonProcessingException e) {
                        return "{\"error\":\"Serialization failed\"}";
                    }
                })
                .orElse("{\"error\":\"Customer not found\"}");
    }

    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable String id, @RequestBody String json) throws JsonProcessingException {
        Customer updatedData = objectMapper.readValue(json, Customer.class);
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setFirstName(updatedData.getFirstName());
                    customer.setLastName(updatedData.getLastName());
                    customer.setMiddleName(updatedData.getMiddleName());
                    customer.setContactNo(updatedData.getContactNo());
                    customer.setStreetAddress(updatedData.getStreetAddress());
                    customer.setStreetAddress2(updatedData.getStreetAddress2());
                    customer.setCity(updatedData.getCity());
                    customer.setStateOrProvince(updatedData.getStateOrProvince());
                    customer.setPostalCode(updatedData.getPostalCode());
                    try {
                        return objectMapper.writeValueAsString(customerRepository.save(customer));
                    } catch (JsonProcessingException e) {
                        return "{\"error\":\"Update failed during serialization\"}";
                    }
                })
                .orElse("{\"error\":\"Customer not found\"}");
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable String id) throws JsonProcessingException {
        Map<String, String> response = new HashMap<>();
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            response.put("message", "Customer deleted successfully.");
        } else {
            response.put("message", "Customer not found.");
        }
        return objectMapper.writeValueAsString(response);
    }

    @GetMapping("/test")
    public String testJackson() throws JsonProcessingException {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Jackson is working!");
        response.put("time", LocalDateTime.now());
        return objectMapper.writeValueAsString(response);
    }

    // Optional: Keep as additional demo endpoints
    @PostMapping("/manual-create")
    public String createFromJson(@RequestBody String json) throws JsonProcessingException {
        Customer customer = objectMapper.readValue(json, Customer.class);
        return objectMapper.writeValueAsString(customerRepository.save(customer));
    }

    @GetMapping("/manual-json/{id}")
    public String getCustomerAsJson(@PathVariable String id) throws JsonProcessingException {
        return customerRepository.findById(id)
                .map(customer -> {
                    try {
                        return objectMapper.writeValueAsString(customer);
                    } catch (JsonProcessingException e) {
                        return "{\"error\":\"Serialization failed\"}";
                    }
                })
                .orElse("{\"error\":\"Customer not found\"}");
    }
}
