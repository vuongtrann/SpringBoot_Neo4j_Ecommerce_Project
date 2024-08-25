package com.spring.ecommerce.persistence.repository;

import com.spring.ecommerce.persistence.model.Category;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends Neo4jRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query(value = "MATCH (c:Category) " +
            " RETURN c.id AS id, c.name AS name, c.totalOfView AS totalOfView, c.totalOfSold AS totalOfSold " +
            " ORDER BY c.totalOfView DESC, c.totalOfSold DESC " +
            " LIMIT $limit")
    List<Category> getTopCategory(@Param("limit") int limit);

}
