package com.spring.ecommerce.persistence.repository;

import com.spring.ecommerce.persistence.model.Product;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReprository extends Neo4jRepository<Product, Long> {
    @Query("MATCH (n:Product) WHERE toLower(n.name) =~ toLower('.*'+ $0 +'.*') RETURN n")
    List<Product> findByNameProduct(@Param("name") String name);


}
