package com.spring.ecommerce.persistence.dto;

import com.spring.ecommerce.persistence.model.Category;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductForm {
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

    private List<Long> categories = new ArrayList<>();


    public ProductForm(String name, List<String> imageURL, String description, double oldPrice, Double rating, int viewCount, int quantitySold, int remainingQuantity, String brandName) {
    }
}
