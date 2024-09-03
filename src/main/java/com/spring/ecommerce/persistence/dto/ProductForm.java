package com.spring.ecommerce.persistence.dto;

import com.spring.ecommerce.persistence.model.ProductDimensions;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductForm {
    private String name;
    private List<String> imageURL;
    private String description;
    private Double msrp;
    private Double salePrice;
    private Double price;
    private int quantity;
    private String SKU;
    private String brandName;

    private String sellingTypes;
    private List<Long> categories = new ArrayList<>();

    private ProductDimensions dimensions;

//    public ProductForm(String name, List<String> imageURL, String description, Double msrp,Double salePrice,Double price,
//                       Double rating, int viewCount, int quantitySold, int remainingQuantity, String brandName) {
//    }
}
