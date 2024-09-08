package com.spring.ecommerce.service;

import com.spring.ecommerce.persistence.model.PaymentModel.StripeCustomer;
import com.spring.ecommerce.persistence.model.PaymentModel.StripeInvoice;
import com.spring.ecommerce.persistence.model.PaymentModel.StripePayment;
import com.stripe.exception.StripeException;

public interface StripeCustomerService {


    // Customers
    public StripeCustomer createCustomer(StripeCustomer stripeCustomer) throws Exception;
    public StripeCustomer updateCustomer(StripeCustomer stripeCustomer) throws Exception, StripeException;
    public void delete(String stripeCustomerId) throws Exception, StripeException;
    public StripeCustomer findById(String customerId) throws Exception, StripeException;


    //Payment
    public StripePayment createPaymentIntent (StripePayment payment) throws StripeException;





}
