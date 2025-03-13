package com.example.salespurchase.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.salespurchase.model.Notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private String notificationType;
    private String itemName;
    private String domainName;
    private Integer saleQuantity;
    private Integer purchaseQuantity;
    private Integer quantityDifference;
    private BigDecimal salePrice;
    private BigDecimal purchasePrice;
    private BigDecimal priceDifference;
    private BigDecimal priceDifferencePercentage;
    private LocalDateTime createdAt;
    private Boolean isRead;
    
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

	// Convert DTO to Entity
    public Notification toEntity() {
        Notification notification = new Notification();
        notification.setId(this.id);
        notification.setNotificationType(this.notificationType);
        notification.setItemName(this.itemName);
        notification.setDomainName(this.domainName);
        notification.setSaleQuantity(this.saleQuantity);
        notification.setPurchaseQuantity(this.purchaseQuantity);
        notification.setQuantityDifference(this.quantityDifference);
        notification.setSalePrice(this.salePrice);
        notification.setPurchasePrice(this.purchasePrice);
        notification.setPriceDifference(this.priceDifference);
        notification.setPriceDifferencePercentage(this.priceDifferencePercentage);
        notification.setCreatedAt(this.createdAt);
        notification.setIsRead(this.isRead);
        return notification;
    }

    // Convert Entity to DTO
    public static NotificationDTO fromEntity(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setNotificationType(notification.getNotificationType());
        dto.setItemName(notification.getItemName());
        dto.setDomainName(notification.getDomainName());
        dto.setSaleQuantity(notification.getSaleQuantity());
        dto.setPurchaseQuantity(notification.getPurchaseQuantity());
        dto.setQuantityDifference(notification.getQuantityDifference());
        dto.setSalePrice(notification.getSalePrice());
        dto.setPurchasePrice(notification.getPurchasePrice());
        dto.setPriceDifference(notification.getPriceDifference());
        dto.setPriceDifferencePercentage(notification.getPriceDifferencePercentage());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setIsRead(notification.getIsRead());
        return dto;
    }
}