package com.spring.ecommerce.persistence.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ProductDimensionsRepository extends Neo4jRepository<com.spring.ecommerce.persistence.model.ProductDimensions, Long> {
}
