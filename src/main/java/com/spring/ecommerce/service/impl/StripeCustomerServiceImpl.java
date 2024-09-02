package com.spring.ecommerce.service.impl;

import com.spring.ecommerce.persistence.model.PaymentModel.*;
import com.spring.ecommerce.persistence.repository.stripeRepository.StripeCustomerRepository;
import com.spring.ecommerce.persistence.repository.stripeRepository.StripeInvoiceRepository;
import com.spring.ecommerce.service.StripeCustomerService;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service

public class StripeCustomerServiceImpl implements StripeCustomerService {

    @Autowired
    private StripeCustomerRepository stripeCustomerRepository;


    @Autowired
    private StripeInvoiceRepository stripeInvoiceRepository;

    public StripeCustomer createCustomer(StripeCustomer customerStripe) throws StripeException, NullPointerException {
        if (!(customerStripe.getName().isEmpty() && customerStripe.getBalance() == 0)) {

            Map<String, Object> prepareData = new HashMap<>();
            prepareData.put("name", customerStripe.getName());
            prepareData.put("balance", customerStripe.getBalance());
            prepareData.put("email", customerStripe.getEmail());
            prepareData.put("phone", customerStripe.getPhone());
            if (customerStripe.getPayment_method()!= null){
                prepareData.put("payment_method", customerStripe.getPayment_method());
                customerStripe.getInvoice_settings().put("default_payment_method", customerStripe.getPayment_method());
                prepareData.put("invoice_settings", customerStripe.getInvoice_settings());
            }
            com.stripe.model.Customer stripeCustomer = com.stripe.model.Customer.create(prepareData);
            if (!Objects.isNull(stripeCustomer)) {
                customerStripe.setId(stripeCustomer.getId());
                stripeCustomerRepository.save(customerStripe);
            }
            return customerStripe;
        }
        else return null;

    }







    public StripePayment createPaymentIntent (StripePayment payment) throws StripeException {
        if (payment != null) {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(payment.getAmount())
                    .setCurrency(payment.getCurrency())
                    .setConfirm(payment.isConfirm())
                    .setDescription("Paid " + payment.getAmount() + payment.getCurrency())
                    .setPaymentMethod(payment.getPayment_method())
                    .setReturnUrl(payment.getReturn_url())
                    .build();
            try {
                PaymentIntent paymentIntent = PaymentIntent.create(params);
                if (paymentIntent != null) {
                    payment.setId(paymentIntent.getId());
                    return payment;
                }
            } catch (StripeException e) {
                System.err.println("Stripe API error: " + e.getMessage());
                throw new RuntimeException("Failed to create PaymentIntent: " + e.getMessage());

            }
        } else {
            throw new RuntimeException("Stripe payment is null");
        }
        return null;
    }


























    public StripeCharge charge(StripeCharge chargeRequest) throws StripeException {
        chargeRequest.setSuccess(false);
        Map<String, Object> param = new HashMap<>();
        param.put("amount", chargeRequest.getAmount());
        param.put("currency", "USD");
        param.put("description", "Payment for id " + chargeRequest.getInfor()
                .getOrDefault("ID_TAG", " "));
        param.put("source", chargeRequest.getStripeToken());

        Map<String, Object> metaData= new HashMap<>();
        metaData.put("id", chargeRequest.getChargeId());
        metaData.putAll(chargeRequest.getInfor());

        param.put("metadata", metaData);

        Charge charge= Charge.create(metaData);
        chargeRequest.setMessage(charge.getOutcome().getSellerMessage());

        if (charge.getPaid()){
            chargeRequest.setChargeId(charge.getId());
            chargeRequest.setSuccess(true);
        }

        return chargeRequest;
    };







}
