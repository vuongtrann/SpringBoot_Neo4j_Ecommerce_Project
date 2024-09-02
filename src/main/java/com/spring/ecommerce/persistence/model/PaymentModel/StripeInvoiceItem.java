package com.spring.ecommerce.persistence.model.PaymentModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StripeInvoiceItem {
    private String id;
    private String invoiceId;
    private String customerId;
    private int amount;



}
