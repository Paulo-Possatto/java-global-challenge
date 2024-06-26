package com.globalpayments.challenge.repository;

import com.globalpayments.challenge.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query("SELECT COUNT(c)>0 FROM CustomerEntity c WHERE c.id = :customerId")
    public boolean isCustomerExisting(
            @Param("customerId") long customerId
    );

    @Query("SELECT c.custPoints FROM CustomerEntity c WHERE c.id = :customerId")
    public String totalCustomerPoints(
            @Param("customerId") long customerId
    );
}
