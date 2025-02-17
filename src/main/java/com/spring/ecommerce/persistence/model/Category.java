package com.spring.ecommerce.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Node("Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int level;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Relationship(type = "HAS_PRODUCTS",direction = Relationship.Direction.OUTGOING)
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

//    @Relationship(type = "Has_parent", direction = Relationship.Direction.OUTGOING)
//    @JsonIgnore
//    private Category categories;

    @Relationship(type = "Has_parent", direction = Relationship.Direction.OUTGOING)
    @JsonIgnore
    private List<Category> categories = new ArrayList<>();

    @Relationship(type = "Has_parent", direction = Relationship.Direction.INCOMING)
    private List<Category> subCategory ;




    @Transient
    private int noOfViews;

    @Transient
    private int productsSold;

    public Category(String name, int level, List<Category> categories) {
        this.name = name;
        this.categories = categories;
        this.level = level;
    }

    //    public Category(String name, Category categories) {
//        this.name = name;
//        this.categories = categories;
//    }
//    /** Constructor*/
//    public Category(String name, Long parentID) {
//        this.name = name;
//        this.parentID = parentID;
//    }
//
//    public Category(Long id) {
//        this.id = id;
//    }
//    public Category(String name) {
//        this.name = name;
//    }




}
