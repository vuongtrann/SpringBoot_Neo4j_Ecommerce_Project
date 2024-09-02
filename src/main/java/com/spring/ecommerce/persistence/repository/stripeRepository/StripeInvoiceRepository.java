package com.spring.ecommerce.persistence.repository.stripeRepository;
import com.spring.ecommerce.persistence.model.PaymentModel.StripeInvoice;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StripeInvoiceRepository extends Neo4jRepository<StripeInvoice, String>{
    @Query("MATCH (n:Invoice{customerId: $customerId}) RETURN n;")
    public List<StripeInvoice> findByCustomerId(String customerId);

}
