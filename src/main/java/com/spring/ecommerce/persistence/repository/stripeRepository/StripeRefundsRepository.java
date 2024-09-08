package com.spring.ecommerce.persistence.repository.stripeRepository;

import com.spring.ecommerce.persistence.model.PaymentModel.StripeRefund;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StripeRefundsRepository extends Neo4jRepository<StripeRefund, String> {
}
