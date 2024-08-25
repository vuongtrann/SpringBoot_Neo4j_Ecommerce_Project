
package com.spring.ecommerce.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;

@Node("Banner")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Banners {

    @Id
    @GeneratedValue
    Long id;
    String url;
    LocalDate startDate;
    LocalDate endDate;




}
