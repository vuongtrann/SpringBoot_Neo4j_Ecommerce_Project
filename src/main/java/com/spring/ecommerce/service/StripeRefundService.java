package com.spring.ecommerce.service;

import com.spring.ecommerce.persistence.model.PaymentModel.StripeRefund;
import com.stripe.exception.StripeException;

import java.util.List;
import java.util.Optional;

public interface StripeRefundService {
    public StripeRefund findById(String id) throws NullPointerException, StripeException;
    public StripeRefund save(StripeRefund stripeRefund)throws NullPointerException, StripeException;
    public StripeRefund update(String refundId, String reason, double amount)throws NullPointerException, StripeException;
    public void delete(String refundId)throws NullPointerException, StripeException;
    public List<StripeRefund> findAll()throws NullPointerException, StripeException;

    public StripeRefund createRefund(String invoiceId, int amount,String reason)throws NullPointerException, StripeException;
}
