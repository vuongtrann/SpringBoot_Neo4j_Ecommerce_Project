package com.spring.ecommerce.persistence.config;

import com.spring.ecommerce.service.StripeService;
import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @Value("${stripe.apiKey}")
    private String secretKey;

    @Bean
    public StripeService stripe() {
        Stripe.apiKey = secretKey;
        return new StripeService();
    }
}
