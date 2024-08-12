package com.spring.ecommerce.persistence.repository;

import com.spring.ecommerce.persistence.model.Category;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends Neo4jRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
