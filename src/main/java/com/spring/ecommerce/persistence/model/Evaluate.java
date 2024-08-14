package com.spring.ecommerce.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("evaluate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evaluate {
    @Id
    @GeneratedValue
    private Long id;
    private int points;
    private String comment;

    @Relationship(type = "BELONG_TO", direction = Relationship.Direction.OUTGOING)
    private Customer customer;

    @Relationship(type = "EVALUATE_FOR", direction = Relationship.Direction.OUTGOING)
    private Product product;


}
