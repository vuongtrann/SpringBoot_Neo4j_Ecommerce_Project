package com.spring.ecommerce.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.math.BigDecimal;
import java.util.*;

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
    private List<String> imageURL;
    private String description;
    private double oldPrice;
    private Double price;
    private Double rating;
    private int viewCount;
    private int quantitySold;
    private int remainingQuantity;
    private String brandName;

    @Relationship(type = "BELONGS_TO", direction = Relationship.Direction.OUTGOING)
    private Set<Category> categories = new HashSet<>();

    public void addCategory(Category category) {
        this.categories.add(category);
        category.getProducts().add(this);
    }
    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.getProducts().remove(this);
    }

//    public Product(String name, String imageURL, String description, Double price) {
//        this.name = name;
//        this.imageURL = Collections.singletonList(imageURL);
//        this.description = description;
//        this.price = price;
//    }
//
//    public Product(String name, String imageURL, String description, Double price, Category category) {
//        this.name = name;
//        this.imageURL = Collections.singletonList(imageURL);
//        this.description = description;
//        this.price = price;
//        this.category = category;
//    }
}
