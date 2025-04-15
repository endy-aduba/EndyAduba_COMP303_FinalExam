package com.ena.spring.finalex.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ena.spring.finalex.models.Customer;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
}
