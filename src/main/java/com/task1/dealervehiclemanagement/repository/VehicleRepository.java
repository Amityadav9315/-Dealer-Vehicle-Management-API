package com.task1.dealervehiclemanagement.repository;

import com.task1.dealervehiclemanagement.model.SubscriptionType;
import com.task1.dealervehiclemanagement.model.Vehicle;
import com.task1.dealervehiclemanagement.model.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
    @Query("SELECT DISTINCT v FROM Vehicle v JOIN FETCH v.dealer")
    @Override
    List<Vehicle> findAll();
    
    @Query("SELECT v FROM Vehicle v JOIN FETCH v.dealer WHERE v.dealer.id = :dealerId")
    List<Vehicle> findByDealerId(@Param("dealerId") Long dealerId);
    
    List<Vehicle> findByStatus(VehicleStatus status);
    
    @Query("SELECT v FROM Vehicle v JOIN FETCH v.dealer WHERE v.dealer.subscriptionType = :subscriptionType")
    List<Vehicle> findByPremiumDealers(@Param("subscriptionType") SubscriptionType subscriptionType);
}

