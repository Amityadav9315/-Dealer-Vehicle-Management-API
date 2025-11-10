package com.task1.dealervehiclemanagement.repository;

import com.task1.dealervehiclemanagement.model.Dealer;
import com.task1.dealervehiclemanagement.model.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {
    Optional<Dealer> findByEmail(String email);
    List<Dealer> findBySubscriptionType(SubscriptionType subscriptionType);
}

