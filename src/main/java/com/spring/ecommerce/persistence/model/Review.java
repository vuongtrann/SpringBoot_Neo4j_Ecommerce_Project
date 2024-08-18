package com.spring.ecommerce.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;


@Node("Evaluate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    private Long customerId;
    private int rating;
    private String comment;




    @JsonIgnore
    @Relationship(type = "BELONG_TO", direction = Relationship.Direction.OUTGOING)
    private Customer customer;

    @JsonIgnore
    @Relationship(type = "EVALUATE_FOR", direction = Relationship.Direction.OUTGOING)
    private Product product;


}
