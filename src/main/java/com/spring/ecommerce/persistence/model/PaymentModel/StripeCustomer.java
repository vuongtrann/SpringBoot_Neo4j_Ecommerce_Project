package com.spring.ecommerce.persistence.model.PaymentModel;

import com.stripe.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.HashMap;
import java.util.Map;

@Node("Stripe-Customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StripeCustomer {
    //require
    @Id
    private String id;
    private String name;
    private int balance;
    private String email;
    private String phone;
    private String currency;
    private String payment_method;
    private String default_Payment_method;
//    private String default_Payment_method;
    @Transient
    private Map<String, String> invoice_settings = new HashMap<>();



    public StripeCustomer stripeCustomer(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
        this.currency = customer.getCurrency();
        return this;


    }


}
