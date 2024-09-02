package com.spring.ecommerce.persistence.model.PaymentModel;

import lombok.Data;

@Data
public class StripePayment {

    private String id;
    private Long amount;
    private String currency = "USD";
    private boolean confirm = true;
    private String description;
    private String  payment_method = "pm_card_visa";
    private String return_url= "https://dashboard.stripe.com/test/payments";




}
