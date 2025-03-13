//package com.example.salespurchase.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "notifications")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Notification {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    
//    @Column(name = "notification_type", columnDefinition = "VARCHAR(145)")
//    private String notificationType;
//    
// // Make sure these fields don't have length constraints that could cause issues
//    @Column(name = "item_name")
//    private String itemName;
//
//    @Column(name = "domain_name")
//    private String domainName;
//    
//    @Column(name = "sale_quantity")
//    private Integer saleQuantity;
//
//    @Column(name = "purchase_quantity")
//    private Integer purchaseQuantity;
//
//    @Column(name = "quantity_difference")
//    private Integer quantityDifference;
//
//    @Column(name = "sale_price")
//    private BigDecimal salePrice;
//
//    @Column(name = "purchase_price")
//    private BigDecimal purchasePrice;
//
//    @Column(name = "price_difference")
//    private BigDecimal priceDifference;
//
//    @Column(name = "price_difference_percentage")
//    private BigDecimal priceDifferencePercentage;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @Column(name = "is_read")
//    private Boolean isRead;
//
//    @PrePersist
//    protected void onCreate() {
//        this.createdAt = LocalDateTime.now();
//        this.isRead = false;
//    }
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public String getNotificationType() {
//		return notificationType;
//	}
//
//	public void setNotificationType(String notificationType) {
//		this.notificationType = notificationType;
//	}
//
//	public String getItemName() {
//		return itemName;
//	}
//
//	public void setItemName(String itemName) {
//		this.itemName = itemName;
//	}
//
//	public String getDomainName() {
//		return domainName;
//	}
//
//	public void setDomainName(String domainName) {
//		this.domainName = domainName;
//	}
//
//	public Integer getSaleQuantity() {
//		return saleQuantity;
//	}
//
//	public void setSaleQuantity(Integer saleQuantity) {
//		this.saleQuantity = saleQuantity;
//	}
//
//	public Integer getPurchaseQuantity() {
//		return purchaseQuantity;
//	}
//
//	public void setPurchaseQuantity(Integer purchaseQuantity) {
//		this.purchaseQuantity = purchaseQuantity;
//	}
//
//	public Integer getQuantityDifference() {
//		return quantityDifference;
//	}
//
//	public void setQuantityDifference(Integer quantityDifference) {
//		this.quantityDifference = quantityDifference;
//	}
//
//	public BigDecimal getSalePrice() {
//		return salePrice;
//	}
//
//	public void setSalePrice(BigDecimal salePrice) {
//		this.salePrice = salePrice;
//	}
//
//	public BigDecimal getPurchasePrice() {
//		return purchasePrice;
//	}
//
//	public void setPurchasePrice(BigDecimal purchasePrice) {
//		this.purchasePrice = purchasePrice;
//	}
//
//	public BigDecimal getPriceDifference() {
//		return priceDifference;
//	}
//
//	public void setPriceDifference(BigDecimal priceDifference) {
//		this.priceDifference = priceDifference;
//	}
//
//	public BigDecimal getPriceDifferencePercentage() {
//		return priceDifferencePercentage;
//	}
//
//	public void setPriceDifferencePercentage(BigDecimal priceDifferencePercentage) {
//		this.priceDifferencePercentage = priceDifferencePercentage;
//	}
//
//	public LocalDateTime getCreatedAt() {
//		return createdAt;
//	}
//
//	public void setCreatedAt(LocalDateTime createdAt) {
//		this.createdAt = createdAt;
//	}
//
//	public Boolean getIsRead() {
//		return isRead;
//	}
//
//	public void setIsRead(Boolean isRead) {
//		this.isRead = isRead;
//	}
//    
//}
package com.example.salespurchase.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "notification_type")
	private String notificationType;

	@Column(name = "item_name")
	private String itemName;

	@Column(name = "domain_name")
	private String domainName;

	@Column(name = "sale_quantity")
	private Integer saleQuantity;

	@Column(name = "purchase_quantity")
	private Integer purchaseQuantity;

	@Column(name = "quantity_difference")
	private Integer quantityDifference;

	@Column(name = "sale_price")
	private BigDecimal salePrice;

	@Column(name = "purchase_price")
	private BigDecimal purchasePrice;

	@Column(name = "price_difference")
	private BigDecimal priceDifference;

	@Column(name = "price_difference_percentage")
	private BigDecimal priceDifferencePercentage;

	@Column(name = "created_at", columnDefinition = "DATETIME")
	private LocalDateTime createdAt;

	@Column(name = "is_read")
	private Boolean isRead;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.isRead = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public Integer getSaleQuantity() {
		return saleQuantity;
	}

	public void setSaleQuantity(Integer saleQuantity) {
		this.saleQuantity = saleQuantity;
	}

	public Integer getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(Integer purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}

	public Integer getQuantityDifference() {
		return quantityDifference;
	}

	public void setQuantityDifference(Integer quantityDifference) {
		this.quantityDifference = quantityDifference;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getPriceDifference() {
		return priceDifference;
	}

	public void setPriceDifference(BigDecimal priceDifference) {
		this.priceDifference = priceDifference;
	}

	public BigDecimal getPriceDifferencePercentage() {
		return priceDifferencePercentage;
	}

	public void setPriceDifferencePercentage(BigDecimal priceDifferencePercentage) {
		this.priceDifferencePercentage = priceDifferencePercentage;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

}