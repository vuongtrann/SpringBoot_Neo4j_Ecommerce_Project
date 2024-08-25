
package com.spring.ecommerce.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;
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
    private Long id;
    private String url;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive = true;





}
