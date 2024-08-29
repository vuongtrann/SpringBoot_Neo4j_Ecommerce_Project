package com.spring.ecommerce.persistence.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class StripeCharge {
    private String stripeToken;
    private String uerName;
    private boolean success;
    private double amount;
    private String message;
    private String chargeId;
    private Map<String, Object> infor= new HashMap<>();

}
