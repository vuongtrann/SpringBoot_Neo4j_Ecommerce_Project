package com.spring.ecommerce.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
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
    private String primaryImageURL;
    private String description;
    private Double msrp;
    private Double salePrice;
    private Double price;
    private Double rating;
    private int viewCount;
    private int quantity;
    private String SKU;
    private int quantitySold;
    private int remainingQuantity;
    private String brandName;

    private String sellingTypes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Relationship(type = "BELONGS_TO", direction = Relationship.Direction.OUTGOING)
    private List<Category> categories = new ArrayList<>();
//    @Relationship(type = "BELONGS_TO", direction = Relationship.Direction.INCOMING)
//    private List<Category> categories = new ArrayList<>();

    @Relationship(type = "HAS_DIMENSIONS",direction = Relationship.Direction.OUTGOING)
    private ProductDimensions dimensions;

//    public void addCategory(Category category) {
//        this.categories.add(category);
//        category.getProducts().add(this);
//    }
//    public void removeCategory(Category category) {
//        this.categories.remove(category);
//        category.getProducts().remove(this);
//    }

//    public Product(String name, String imageURL, String description, Double price) {
//        this.name = name;
//        this.imageURL = Collections.singletonList(imageURL);
//        this.description = description;
//        this.price = price;
//    }
//

    public Product(String name, String description, Double msrp, Double salePrice, Double price, int quantity,
                   String SKU, String sellingTypes, List<Category> categories, ProductDimensions dimensions) {
        this.name = name;
        this.description = description;
        this.msrp = msrp;
        this.salePrice = salePrice;
        this.price = price;
        this.quantity = quantity;
        this.SKU = SKU;
        this.sellingTypes = sellingTypes;
        this.categories = categories;
        this.dimensions = dimensions;
    }
}
