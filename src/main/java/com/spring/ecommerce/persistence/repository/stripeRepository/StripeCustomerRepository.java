package com.spring.ecommerce.persistence.repository.stripeRepository;

import com.spring.ecommerce.persistence.model.PaymentModel.StripeCustomer;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface StripeCustomerRepository extends Neo4jRepository<StripeCustomer,String> {
}
