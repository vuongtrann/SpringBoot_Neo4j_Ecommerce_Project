package com.spring.ecommerce.service;

import com.spring.ecommerce.persistence.model.PaymentModel.StripeCustomer;
import com.spring.ecommerce.persistence.model.PaymentModel.StripeInvoice;
import com.spring.ecommerce.persistence.model.PaymentModel.StripePayment;
import com.stripe.exception.StripeException;

public interface StripeCustomerService {


    // Customers
    public StripeCustomer createCustomer(StripeCustomer stripeCustomer) throws Exception;

    //Payment
    public StripePayment createPaymentIntent (StripePayment payment) throws StripeException;





}
