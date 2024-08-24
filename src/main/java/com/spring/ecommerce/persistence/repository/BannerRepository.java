package com.spring.ecommerce.persistence.repository;

import com.spring.ecommerce.persistence.model.Banners;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends Neo4jRepository <Banners, Long>{

}