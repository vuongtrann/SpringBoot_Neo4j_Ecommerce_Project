package com.spring.ecommerce.service.impl;

import com.spring.ecommerce.persistence.model.PaymentModel.StripeInvoice;
import com.spring.ecommerce.persistence.model.PaymentModel.StripeInvoiceItem;
import com.spring.ecommerce.persistence.repository.stripeRepository.StripeCustomerRepository;
import com.spring.ecommerce.persistence.repository.stripeRepository.StripeInvoiceRepository;
import com.spring.ecommerce.service.StripeInvoiceService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceItem;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeInvoiceServiceImpl implements StripeInvoiceService {

    @Autowired
    private StripeInvoiceRepository stripeInvoiceRepository;

    @Autowired
    private StripeCustomerRepository stripeCustomerRepository;
    @Qualifier("webRequest")
    @Autowired
    private WebRequest webRequest;


    public StripeInvoice createInvoice (String customerId) throws NullPointerException, StripeException {
        Customer customer = Customer.retrieve(customerId);

        if (customer.getInvoiceSettings().getDefaultPaymentMethod().isEmpty()) {
            //update logic here
            return null;

        }

        if (stripeCustomerRepository.existsById(customerId)) {
            Map<String, Object> prepareData = new HashMap<>();
            prepareData.put("customer", customerId);
            if (customer.getCurrency() != null) {
                prepareData.put("currency", customer.getCurrency());
            }
            Invoice invoice = Invoice.create(prepareData);
            if (invoice != null) {
                StripeInvoice stripeInvoice = new StripeInvoice(invoice);
                stripeInvoice.setStripeCustomer(stripeCustomerRepository.findById(customerId).get());
                return stripeInvoiceRepository.save(stripeInvoice);

            }

        }

        else {
            throw new RuntimeException("Stripe customer not found: " + customerId);

        }
        return null;
    }

    @Override
    public List<StripeInvoice> findInvoiceByCustomer(String customer) throws StripeException, NullPointerException{
        return stripeInvoiceRepository.findByCustomerId(customer);

    }


    public StripeInvoice createInvoiceItem(StripeInvoiceItem item) throws StripeException{
        if (item.getCustomerId() == null && item.getInvoiceId() == null) {
            return null;
        }

        else{
            Customer.retrieve(item.getCustomerId());
            Invoice.retrieve(item.getInvoiceId());

            Map<String, Object> prepareData = new HashMap<>();
            prepareData.put("customer", item.getCustomerId());
            prepareData.put("invoice", item.getInvoiceId());
            prepareData.put("amount", item.getAmount());

            InvoiceItem invoiceItem = InvoiceItem.create(prepareData);
            if (invoiceItem != null) {
                StripeInvoice stripeInvoice = stripeInvoiceRepository.
                        findById(item.getInvoiceId()).orElseThrow(() -> new RuntimeException("Stripe invoice not found"));
                stripeInvoice.additem(invoiceItem.getId());
                stripeInvoice.setTotalAmount(stripeInvoice.getTotalAmount() + item.getAmount());
                return stripeInvoiceRepository.save(stripeInvoice);

            }
        }


        return null;
    }


    public StripeInvoice deleteInvoiceItem(String invoiceItemId) throws StripeException{
        return null;
    }


    @Override
    public StripeInvoice finalize(String invoiceId) throws StripeException{
        StripeInvoice stripeInvoice = stripeInvoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Stripe invoice not found"));
        Invoice invoice = Invoice.retrieve(invoiceId).finalizeInvoice();
        stripeInvoice.setStatus(invoice.getStatus());

        return stripeInvoiceRepository.save(stripeInvoice);
    }


    @Override
    public StripeInvoice payInvoice(String invoiceId) throws StripeException{
        if (invoiceId == null || invoiceId.isEmpty()) {
            return null;
        }

        StripeInvoice stripeInvoice = stripeInvoiceRepository.findById(invoiceId).orElseThrow(() -> new RuntimeException("Stripe invoice not found"));
        if (stripeInvoice.getStatus().equals("open")) {
            Invoice invoice = Invoice.retrieve(invoiceId).pay();
            stripeInvoice.setStatus(invoice.getStatus());
            return stripeInvoiceRepository.save(stripeInvoice);
        }

        return null;
    }




}
