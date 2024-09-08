package com.spring.ecommerce.persistence.repository.stripeRepository;

import com.spring.ecommerce.persistence.model.PaymentModel.StripeCustomer;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StripeCustomerRepository extends Neo4jRepository<StripeCustomer,String> {
    @Query("MATCH (n:`Stripe-Customer`) WHERE n.id= $customerId RETURN n")
    Optional<StripeCustomer> findCustomerById(@Param("customerId") String customerId);
}
