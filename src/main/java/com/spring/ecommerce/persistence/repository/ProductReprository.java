package com.spring.ecommerce.persistence.repository;

import com.spring.ecommerce.persistence.model.Product;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReprository extends Neo4jRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
