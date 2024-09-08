package com.spring.ecommerce.controller;

import com.spring.ecommerce.persistence.model.PaymentModel.StripeInvoiceItem;
import com.spring.ecommerce.service.StripeInvoiceService;
import com.spring.ecommerce.service.impl.StripeRefundsServiceImpl;
import com.spring.ecommerce.util.RestResponse;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/stripe/invoice")

public class StripeInvoiceController {


    @Autowired
    private StripeInvoiceService stripeInvoiceService;
    @Autowired
    private StripeRefundsServiceImpl stripeRefundsService;


    @RequestMapping(value ="/item" , method =RequestMethod.POST )
    public RestResponse addItem(@RequestBody StripeInvoiceItem item) throws StripeException , NullPointerException {
        if (item.getInvoiceId() == null && item.getCustomerId()== null) {
//            throw new NullPointerException("item is null");
            return RestResponse.builder().message("item is null").build();
        }
        else {
            return RestResponse.builder(stripeInvoiceService.createInvoiceItem(item)).message("Success").build();

        }


    }



    @RequestMapping(value ="/{invoiceId}/finalize" , method = RequestMethod.PUT)
    public RestResponse finalizeItem(@PathVariable("invoiceId") String invoiceId) throws StripeException {
        if (invoiceId == null) {
            return RestResponse.builder().message("invoiceId is null").build();
        }
        return RestResponse.builder(stripeInvoiceService.finalize(invoiceId)).message("Success").build();
    }


    @RequestMapping(value = "/{invoiceId}/refunds", method = RequestMethod.POST)
    public RestResponse refundItem(@PathVariable("invoiceId") String invoiceId,
                                    @RequestParam("amount") int amount,
                                   @RequestParam("reason") String reason) throws StripeException {
        if (invoiceId.isEmpty() || amount <= 0) {
            return RestResponse.builder().message("Error").build();
        }

        else return RestResponse.builder(stripeRefundsService.createRefund(invoiceId, amount, reason)).message("Success").build();
    }













}
