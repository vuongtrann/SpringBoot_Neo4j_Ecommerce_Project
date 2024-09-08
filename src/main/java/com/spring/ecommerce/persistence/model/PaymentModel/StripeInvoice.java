package com.spring.ecommerce.persistence.model.PaymentModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stripe.model.Invoice;
import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node("Invoice")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StripeInvoice {

    @Id
    private String id;
    private String customerId;
    private String status;
    private String description;
    private String currency="usd";
    private List<String> invoiceItemId = new ArrayList<>();
    private double amount_due;
    private String chargeId;
    private String paymentIntent;


    @Relationship(value = "BELONG_TO", direction = Relationship.Direction.OUTGOING)
    @JsonIgnore
    private StripeCustomer stripeCustomer;


    public StripeInvoice (Invoice invoice) {
        this.id = invoice.getId();
        this.currency = invoice.getCurrency();
        this.description = invoice.getDescription();
        this.customerId = invoice.getCustomer();
        this.status = invoice.getStatus();
        this.amount_due = invoice.getAmountDue();
        this.chargeId = invoice.getCharge();
        this.paymentIntent = invoice.getPaymentIntent();

    }


}