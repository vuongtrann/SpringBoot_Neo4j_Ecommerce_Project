package com.spring.ecommerce.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private Long parentID;

    @Relationship(type = "HAS_PRODUCT",direction = Relationship.Direction.INCOMING)
    @JsonIgnore
    private Product product;

    @Relationship(type = "Has_parent", direction = Relationship.Direction.OUTGOING)
    @JsonIgnore
    private Category parent;

    public Category(String name, Long parentID) {
        this.name = name;
        this.parentID = parentID;
    }

    public Category(Long id) {
        this.id = id;
    }
}
