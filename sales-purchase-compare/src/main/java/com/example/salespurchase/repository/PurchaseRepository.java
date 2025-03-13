package com.example.salespurchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.salespurchase.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findBySubscription(String subscription);
    
    List<Purchase> findByDomainName(String domainName);
    
    List<Purchase> findBySubscriptionAndDomainName(String subscription, String domainName);
}