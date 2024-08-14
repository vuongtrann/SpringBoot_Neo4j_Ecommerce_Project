package com.spring.ecommerce.persistence.repository;

import com.spring.ecommerce.persistence.model.Evaluate;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluateRepository extends Neo4jRepository<Evaluate, Long> {
}
