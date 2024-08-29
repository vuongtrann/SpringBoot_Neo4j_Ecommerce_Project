package com.spring.ecommerce.service;

import com.spring.ecommerce.persistence.model.StripeCharge;
import com.spring.ecommerce.persistence.model.StripeToken;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service

public class StripeService {
//
//    @Value("$stripe.apiKey")
//    private String key;
//
//    @PostConstruct
//    public void init() {
//        Stripe.apiKey = this.key;
//    }


    public StripeToken createStripeToken(StripeToken stripeToken) {
        try {
            Map<String, Object> card = new HashMap<>();
            card.put("number", stripeToken.getCardNumber());
            card.put("exp_year", stripeToken.getExpYear());
            card.put("exp_month", stripeToken.getExpMonth());
//            card.put("ccv", stripeToken.getCcv());
            card.put("customer", stripeToken.getUserName());
            Map<String, Object> param = new HashMap<>();
            param.put("card", card);
            Token token = Token.create(param);
            if (token != null && token.getId() != null) {
                stripeToken.setSuccess(true);
                stripeToken.setToken(token.getId());
            }

            return stripeToken;

        }catch ( StripeException e ) {
            throw new RuntimeException(e);
        }

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




    public PaymentIntent paymentIntent (){

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(2000L)
                        .setCurrency("usd")
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        try {
            return PaymentIntent.create(params);
        } catch (StripeException e) {
            System.err.println("Stripe API error: " + e.getMessage());
            throw new RuntimeException("Failed to create PaymentIntent: " + e.getMessage());

        }
    }


}
