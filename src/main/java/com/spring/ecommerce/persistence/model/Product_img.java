package com.spring.ecommerce.persistence.model;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Product_img")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product_img {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String imageURL;
    private String description;
    private Double price;
    private Double ratting;
    private String evaluatting;
    @Relationship(type = "BELONGS_TO", direction = Relationship.Direction.OUTGOING)
    private Category category;

}
