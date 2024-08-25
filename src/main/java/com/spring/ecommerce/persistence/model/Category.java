package com.spring.ecommerce.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.core.schema.*;

@Node("Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {


//    @Property("id")
//    @Generated
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Long parentID;


//    @Relationship(type = "HAS_PRODUCT",direction = Relationship.Direction.INCOMING)
//    @JsonIgnore
//    private Product product;

    @Relationship(type = "HAS_PARENT", direction = Relationship.Direction.OUTGOING)
    @JsonIgnore
    private Category parent;

    @Transient
    private int noOfViews;

    @Transient
    private int productsSold;


    /** Constructor*/
    public Category(String name, Long parentID) {
        this.name = name;
        this.parentID = parentID;
    }

    public Category(Long id) {
        this.id = id;
    }
    public Category(String name) {
        this.name = name;
    }





}
