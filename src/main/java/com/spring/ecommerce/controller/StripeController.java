package com.spring.ecommerce.controller;

import com.spring.ecommerce.persistence.model.StripeCharge;
import com.spring.ecommerce.persistence.model.StripeToken;
import com.spring.ecommerce.service.S3Service;
import com.spring.ecommerce.service.StripeService;
import com.spring.ecommerce.util.RestResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")

public class StripeController {



//   private final StripeService stripeService;

   @Autowired
   private StripeService stripeService;


    @PostMapping("/stripe/card/token")
    public RestResponse createCardToken(@RequestBody StripeToken stripeToken) throws StripeException {
        System.out.println("entered");

        return RestResponse.builder(stripeService.createStripeToken(stripeToken)).message("ok").build();
    }


    @PostMapping("/stripe/charge")
    @ResponseBody
    public StripeCharge charge (@RequestBody StripeCharge stripeCharge) throws StripeException {
        return stripeService.charge(stripeCharge);
    }




    @PostMapping("/stripe/pay")
    @ResponseBody
    public PaymentIntent paymentIntent () throws StripeException {
        return stripeService.paymentIntent();
    }
}
