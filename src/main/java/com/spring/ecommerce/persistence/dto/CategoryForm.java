package com.spring.ecommerce.persistence.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryForm {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int level;

    List<Long> categories = new ArrayList<>();

}
