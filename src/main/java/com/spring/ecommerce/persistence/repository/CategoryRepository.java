package com.spring.ecommerce.persistence.repository;

import com.spring.ecommerce.persistence.model.Category;
import com.spring.ecommerce.persistence.model.Product;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CategoryRepository extends Neo4jRepository<Category, Long> {
    Optional<Category> findByName(String name);
    @Query(value = "MATCH (c:Category)<-[:BELONG_TO]-(p:Product) " +
            "WITH c, SUM(p.quantitySold) AS totalSold, SUM(p.viewCount) AS totalView " +
            "RETURN c{.*, id: ID(c), noOfViews: totalView, productsSold: totalSold } " +
            "ORDER BY totalSold DESC, totalView DESC " +
            "LIMIT 10")
    List<Map<String, Object>> getTopCategory(@Param("limit") int limit);

    @Query("MATCH (c:Category) WHERE toLower(c.name) =~ toLower('.*'+ $0 +'.*') RETURN c")
    List<Category> findByCategoryName(String name);

    @Query("MATCH (p:Product)-[:BELONGS_TO]->(c:Category) WHERE id(p)=$0 RETURN c")
    List<Category> getAllCategoryByProductId(Long id);


    @Query("MATCH (n:Category)-[r:Has_parent]->(c:Category) WHERE c.level= 1 RETURN c")
    List<Category> getAllCategory();


}
