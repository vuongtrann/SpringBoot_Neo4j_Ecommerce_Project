package com.spring.ecommerce.persistence.model.PaymentModel;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Data
@Node("Refund")
public class StripeRefund {
    @Id
    private String id;
    private double amount;
    @Relationship(value = "REFUNDS_FOR", direction = Relationship.Direction.OUTGOING)
    private StripeInvoice invoice;
    private String paymentId;
    private String reason;
    private String status;


}
