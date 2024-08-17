package com.spring.ecommerce.persistence.repository;

import com.spring.ecommerce.persistence.model.Evaluate;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;




@Repository
public interface EvaluateRepository extends Neo4jRepository<Evaluate, Long> {


    @Query("Match (e:Evaluate) -[:EVALUATE_FOR]-> (p:Product) where id(p) = $productId Return e")
    public List<Evaluate> findEvaluateByProductId(@Param("productId") Long productId);
}
