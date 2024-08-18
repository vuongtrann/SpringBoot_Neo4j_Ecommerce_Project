package com.spring.ecommerce.persistence.repository;

import com.spring.ecommerce.persistence.model.Review;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;




@Repository
public interface ReviewRepository extends Neo4jRepository<Review, Long> {


    @Query("Match (e:Evaluate) -[:EVALUATE_FOR]-> (p:Product) where id(p) = $productId Return e")
    public List<Review> findEvaluateByProductId(@Param("productId") Long productId);
}
