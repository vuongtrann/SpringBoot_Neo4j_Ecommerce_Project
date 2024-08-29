package com.spring.ecommerce.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StripeToken {
    private String token;
    private String cardNumber;
    private String expYear;
    private String expMonth;
    private String ccv;
    private String userName;
    private boolean success;


}
