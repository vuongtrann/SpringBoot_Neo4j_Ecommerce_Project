package com.spring.ecommerce.service;

import com.spring.ecommerce.persistence.model.PaymentModel.StripeInvoice;
import com.spring.ecommerce.persistence.model.PaymentModel.StripeInvoiceItem;
import com.stripe.exception.StripeException;

import java.util.List;

public interface StripeInvoiceService {
    public StripeInvoice createInvoice(String customerId) throws StripeException;
    public List<StripeInvoice> findInvoiceByCustomer(String customerId) throws StripeException;
    public StripeInvoice createInvoiceItem(StripeInvoiceItem item) throws StripeException;
    public StripeInvoice finalize(String invoiceId) throws StripeException;
    public StripeInvoice payInvoice(String invoiceId) throws StripeException;
}
