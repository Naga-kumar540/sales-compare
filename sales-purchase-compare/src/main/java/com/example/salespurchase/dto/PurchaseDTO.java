package com.example.salespurchase.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.salespurchase.model.Purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {
    private Long id;
    private String console;
    private String domainName;
    private String subscription;
    private String description;
    private String orderName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer quantity;
    private String poNumber;
    private BigDecimal amount;
    private String customerId;
    private String skuId;
    private BigDecimal ratePerUnit;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getSubscription() {
		return subscription;
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public BigDecimal getRatePerUnit() {
		return ratePerUnit;
	}

	public void setRatePerUnit(BigDecimal ratePerUnit) {
		this.ratePerUnit = ratePerUnit;
	}

	// Convert DTO to Entity
    public Purchase toEntity() {
        Purchase purchase = new Purchase();
        purchase.setId(this.id);
        purchase.setConsole(this.console);
        purchase.setDomainName(this.domainName);
        purchase.setSubscription(this.subscription);
        purchase.setDescription(this.description);
        purchase.setOrderName(this.orderName);
        purchase.setStartDate(this.startDate);
        purchase.setEndDate(this.endDate);
        purchase.setQuantity(this.quantity);
        purchase.setPoNumber(this.poNumber);
        purchase.setAmount(this.amount);
        purchase.setCustomerId(this.customerId);
        purchase.setSkuId(this.skuId);
        purchase.setRatePerUnit(this.ratePerUnit);
        return purchase;
    }

    // Convert Entity to DTO
    public static PurchaseDTO fromEntity(Purchase purchase) {
        PurchaseDTO dto = new PurchaseDTO();
        dto.setId(purchase.getId());
        dto.setConsole(purchase.getConsole());
        dto.setDomainName(purchase.getDomainName());
        dto.setSubscription(purchase.getSubscription());
        dto.setDescription(purchase.getDescription());
        dto.setOrderName(purchase.getOrderName());
        dto.setStartDate(purchase.getStartDate());
        dto.setEndDate(purchase.getEndDate());
        dto.setQuantity(purchase.getQuantity());
        dto.setPoNumber(purchase.getPoNumber());
        dto.setAmount(purchase.getAmount());
        dto.setCustomerId(purchase.getCustomerId());
        dto.setSkuId(purchase.getSkuId());
        dto.setRatePerUnit(purchase.getRatePerUnit());
        return dto;
    }
}