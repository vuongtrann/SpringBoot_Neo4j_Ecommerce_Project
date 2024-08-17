package com.spring.ecommerce.persistence.repository;

import com.spring.ecommerce.persistence.model.Product;
import com.spring.ecommerce.persistence.model.Product_img;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReprository_img extends Neo4jRepository<Product_img, Long> {
    Optional<Product_img> findByName(String name);
}
