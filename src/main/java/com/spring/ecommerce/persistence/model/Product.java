package com.spring.ecommerce.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String imageURL;
    private String description;
    private Double price;
    private Double rating;
    @Relationship(type = "BELONG_TO", direction = Relationship.Direction.OUTGOING)
    private Category category;

    public Product(String name, String imageURL, String description, Double price) {
        this.name = name;
        this.imageURL = imageURL;
        this.description = description;
        this.price = price;
    }

    public Product(String name, String imageURL, String description, Double price, Category category) {
        this.name = name;
        this.imageURL = imageURL;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}
