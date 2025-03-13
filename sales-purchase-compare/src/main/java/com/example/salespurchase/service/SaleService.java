package com.example.salespurchase.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.salespurchase.exception.ResourceNotFoundException;
import com.example.salespurchase.model.Sale;
import com.example.salespurchase.repository.SaleRepository;

@Service
//@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public Optional<Sale> findById(Long id) {
        return saleRepository.findById(id);
    }

    @Transactional
    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!saleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sale", "id", id);
        }
        saleRepository.deleteById(id);
    }

    public List<Sale> findByItemName(String itemName) {
        return saleRepository.findByItemName(itemName);
    }

    public List<Sale> findByItemDesc(String itemDesc) {
        return saleRepository.findByItemDesc(itemDesc);
    }

    public List<Sale> findByItemNameAndItemDescContaining(String itemName, String itemDesc) {
        return saleRepository.findByItemNameAndItemDescContaining(itemName, itemDesc);
    }
    
    public List<Sale> findByItemDescContaining(String domainName) {
        return saleRepository.findByItemDescContaining(domainName);
    }
    
    @Transactional
    public List<Sale> saveAll(List<Sale> sales) {
        return saleRepository.saveAll(sales);
    }
    
    // Advanced search method with multiple criteria
    public List<Sale> findBySearchCriteria(String itemName, String itemDesc, LocalDate startDate, LocalDate endDate) {
        List<Specification<Sale>> specifications = new ArrayList<>();
        
        // Build dynamic specifications based on provided criteria
        if (itemName != null && !itemName.isEmpty()) {
            specifications.add((root, query, criteriaBuilder) -> 
                criteriaBuilder.like(criteriaBuilder.lower(root.get("itemName")), "%" + itemName.toLowerCase() + "%"));
        }
        
        if (itemDesc != null && !itemDesc.isEmpty()) {
            specifications.add((root, query, criteriaBuilder) -> 
                criteriaBuilder.like(criteriaBuilder.lower(root.get("itemDesc")), "%" + itemDesc.toLowerCase() + "%"));
        }
        
        if (startDate != null) {
            specifications.add((root, query, criteriaBuilder) -> 
                criteriaBuilder.greaterThanOrEqualTo(root.get("invoiceDate"), startDate));
        }
        
        if (endDate != null) {
            specifications.add((root, query, criteriaBuilder) -> 
                criteriaBuilder.lessThanOrEqualTo(root.get("invoiceDate"), endDate));
        }
        
        // If no specifications, return all sales
        if (specifications.isEmpty()) {
            return saleRepository.findAll();
        }
        
        // Combine all specifications with AND
        Specification<Sale> finalSpec = specifications.get(0);
        for (int i = 1; i < specifications.size(); i++) {
            finalSpec = finalSpec.and(specifications.get(i));
        }
        
        return saleRepository.findAll(finalSpec);
    }
}