package com.example.salespurchase.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.salespurchase.model.Purchase;
import com.example.salespurchase.repository.PurchaseRepository;

@Service
//@RequiredArgsConstructor
public class PurchaseService {
	
    private final PurchaseRepository purchaseRepository;
    public PurchaseService(PurchaseRepository purchaseRepository) {
    	this.purchaseRepository = purchaseRepository;
    }
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public Optional<Purchase> findById(Long id) {
        return purchaseRepository.findById(id);
    }

    @Transactional
    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    @Transactional
    public void deleteById(Long id) {
        purchaseRepository.deleteById(id);
    }

    public List<Purchase> findBySubscription(String subscription) {
        return purchaseRepository.findBySubscription(subscription);
    }

    public List<Purchase> findByDomainName(String domainName) {
        return purchaseRepository.findByDomainName(domainName);
    }

    public List<Purchase> findBySubscriptionAndDomainName(String subscription, String domainName) {
        return purchaseRepository.findBySubscriptionAndDomainName(subscription, domainName);
    }
    
    @Transactional
    public List<Purchase> saveAll(List<Purchase> purchases) {
        return purchaseRepository.saveAll(purchases);
    }
}