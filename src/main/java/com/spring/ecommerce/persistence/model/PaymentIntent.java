package com.spring.ecommerce.persistence.model;

import lombok.Data;
import lombok.Generated;

@Data
public class PaymentIntent {
    @Generated
    private String id;
    private String object= "object";
    private int amount;
    private String currency = "usd";

}
