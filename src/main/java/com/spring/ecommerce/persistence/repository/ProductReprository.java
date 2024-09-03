package com.spring.ecommerce.persistence.repository;

import com.spring.ecommerce.persistence.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReprository extends Neo4jRepository<Product, Long> {

    @Query("MATCH (n:Product)-[r:BELONGS_TO]->(c:Category) return n,c order by n.createdAt DESC")
    List<Product> getAllProductDESC();
    @Query("MATCH (n:Product)-[r:BELONGS_TO]->(c:Category) return n,c order by n.createdAt ASC")
    List<Product> getAllProductASC();

    @Query("MATCH (n:Product) WHERE toLower(n.name) =~ toLower('.*'+ $0 +'.*') RETURN n")
    List<Product> findByNameProduct(@Param("name") String name);

    @Query("MATCH (n:Product)-[r:BELONGS_TO]->(c:Category) WHERE id(c)=$0 RETURN n")
    List<Product> getAllProductByCategoryId(Long id);

    List<Product> findAll(Sort sort);
}
