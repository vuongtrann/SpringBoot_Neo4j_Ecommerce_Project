package com.spring.ecommerce.controller;

import com.spring.ecommerce.persistence.model.PaymentModel.StripeCustomer;
import com.spring.ecommerce.persistence.model.PaymentModel.StripePayment;
import com.spring.ecommerce.service.StripeInvoiceService;
import com.spring.ecommerce.service.impl.StripeCustomerServiceImpl;
import com.spring.ecommerce.service.impl.StripeInvoiceServiceImpl;
import com.spring.ecommerce.util.RestResponse;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/stripe/customers")

public class StripeCustomerController {


    @Autowired
   private StripeCustomerServiceImpl stripeCustomerService;

    @Autowired
    private StripeInvoiceServiceImpl stripeInvoiceService;



    @RequestMapping(value = "", method = RequestMethod.POST)
    public RestResponse createCustomer (@RequestBody StripeCustomer stripeCustomer) throws StripeException {
        if (stripeCustomer != null) {
            return RestResponse.builder(stripeCustomerService.createCustomer(stripeCustomer)).message("Success").build();
        }
        else return RestResponse.builder().message("Error").build();

    }






    @RequestMapping(value ="/{customerId}/invoice" , method = RequestMethod.POST)
    public RestResponse createInvoice(@PathVariable("customerId")  String customerId) throws StripeException, NullPointerException {
        System.out.println(customerId);
        if (customerId == null || customerId.isEmpty()) {
            throw new NullPointerException("customerId cannot be null or empty");
        }
        return RestResponse.builder(stripeInvoiceService.createInvoice(customerId)).message("Invoice created successfully").build();


    }





    @RequestMapping(value ="/{customerId}/invoice" , method = RequestMethod.GET )
    public RestResponse getInvoice(@PathVariable("customerId")  String customerId) throws StripeException {
        if (customerId == null || customerId.isEmpty()) {
            throw new NullPointerException("customerId cannot be null or empty");
        }
        return RestResponse.builder(stripeInvoiceService.findInvoiceByCustomer(customerId))
                .message("Invoice retrieved successfully").build();
    }


    @RequestMapping(value = "/invoice/{invoiceId}/pay", method = RequestMethod.POST)
    public RestResponse pay (@PathVariable String invoiceId) throws StripeException {
        if (invoiceId == null || invoiceId.isEmpty()) {
            throw new NullPointerException("invoiceId cannot be null or empty");
        }
        return RestResponse.builder(stripeInvoiceService.payInvoice(invoiceId)).message("Payment successfully").build();
    }








//    @RequestMapping(value = "/pay",method = RequestMethod.POST)
//    public RestResponse paymentIntent (@RequestBody StripePayment payment) throws StripeException {
//        if (payment != null) {
//            return RestResponse.builder(stripeCustomerService.createPaymentIntent(payment)).message("Success").build();
//        }
//        else return RestResponse.builder().message("Error").build();
//    }
//



//
//    @RequestMapping(value = "/card",method = RequestMethod.POST)
//    public RestResponse createCardToken(@RequestBody StripeToken stripeToken) throws StripeException {
//
//        return RestResponse.builder(stripeServiceImpl.createStripeToken(stripeToken)).message("ok").build();
//    }
//
//
//
//    @RequestMapping(value = "/charge",method = RequestMethod.POST)
//    public StripeCharge charge (@RequestBody StripeCharge stripeCharge) throws StripeException {
//        return stripeServiceImpl.charge(stripeCharge);
//    }





}
