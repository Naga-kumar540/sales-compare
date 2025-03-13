package com.example.salespurchase.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.salespurchase.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByItemName(String itemName);
    
    List<Sale> findByItemDesc(String itemDesc);
    
    @Query("SELECT s FROM Sale s WHERE s.itemName = ?1 AND s.itemDesc LIKE %?2%")
    List<Sale> findByItemNameAndItemDescContaining(String itemName, String itemDesc);
    
    // Find sales with matching domain name in item description
    @Query("SELECT s FROM Sale s WHERE s.itemDesc LIKE %?1%")
    List<Sale> findByItemDescContaining(String domainName);

	List<Sale> findAll(Specification<Sale> finalSpec);
}