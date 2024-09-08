package com.spring.ecommerce.service.impl;

import com.spring.ecommerce.persistence.model.PaymentModel.StripeInvoice;
import com.spring.ecommerce.persistence.model.PaymentModel.StripeRefund;
import com.spring.ecommerce.persistence.repository.stripeRepository.StripeRefundsRepository;
import com.spring.ecommerce.service.StripeRefundService;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeRefundsServiceImpl  implements StripeRefundService {

    @Autowired
    private StripeRefundsRepository stripeRefundsRepository;
    @Autowired
    private StripeInvoiceServiceImpl stripeInvoiceService;

    @Override
    public StripeRefund findById(String id)  {
        StripeRefund stripeRefund = stripeRefundsRepository.findById(id).orElseThrow(()-> new RuntimeException("Not found"));
        return stripeRefund;
    }

    @Override
    public StripeRefund save(StripeRefund stripeRefund) {
        if (stripeRefund.getInvoice() == null) {
            return null;
        }
        return stripeRefundsRepository.save(stripeRefund);
    }

    @Override
    public StripeRefund update(String refundId, String reason, double amount) {
        StripeRefund stripeRefund = findById(refundId);
        if (stripeRefund !=null) {
            stripeRefund.setReason(reason);
            stripeRefund.setAmount(amount);
            return stripeRefundsRepository.save(stripeRefund);
        }
        return null;
    }

    @Override
    public void delete(String refundId) {
        stripeRefundsRepository.deleteById(refundId);
    }

    @Override
    public List<StripeRefund> findAll() {
        return stripeRefundsRepository.findAll();
    }




    @Override
    public StripeRefund createRefund(String invoiceId, int amount, String reason)throws NullPointerException, StripeException{
        StripeInvoice invoice  = stripeInvoiceService.findInvoiceById(invoiceId);
        if (invoice == null ) {
            throw new NullPointerException("Invoice not found");
        }
        if (invoice.getStatus().equals("paid") && !invoice.getPaymentIntent().isEmpty()) {
            Map<String, Object> prepareData = new HashMap<String, Object>();
            prepareData.put("amount", amount);
            prepareData.put("payment_intent", invoice.getPaymentIntent());

            Refund refund = Refund.create(prepareData);
            if (refund != null) {
                StripeRefund stripeRefund = new StripeRefund();
                stripeRefund.setId(refund.getId());
                stripeRefund.setAmount(amount);
                stripeRefund.setStatus(refund.getStatus());
                stripeRefund.setReason(reason);
                stripeRefund.setInvoice(invoice);
                return stripeRefundsRepository.save(stripeRefund);

            }
        }
        return null;
    }







}
