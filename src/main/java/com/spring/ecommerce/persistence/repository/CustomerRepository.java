package com.spring.ecommerce.persistence.repository;

import com.spring.ecommerce.persistence.model.Customer;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends Neo4jRepository<Customer , Long > {
}
