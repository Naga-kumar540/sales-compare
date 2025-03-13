package com.example.salespurchase.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.salespurchase.model.Notification;
import com.example.salespurchase.model.Purchase;
import com.example.salespurchase.model.Sale;

@Service
//@RequiredArgsConstructor
public class ComparisonService {

    private final SaleService saleService;
    private final PurchaseService purchaseService;
    private final NotificationService notificationService;
    

    public ComparisonService(SaleService saleService, PurchaseService purchaseService,
			NotificationService notificationService) {
		this.saleService = saleService;
		this.purchaseService = purchaseService;
		this.notificationService = notificationService;
	}

	/**
     * Generate notifications for quantity and price variations between sales and purchases
     * This method compares the sales and purchases data and creates notifications
     * when there are discrepancies in quantity or price
     */
    @Transactional
    @Scheduled(cron = "0 0 * * * *") // Run hourly
    public void generateNotifications() {
        // Get all purchases
        List<Purchase> purchases = purchaseService.findAll();
        
        for (Purchase purchase : purchases) {
            // For each purchase, find matching sales by subscription(itemName) and domain
            String subscription = purchase.getSubscription();
            String domainName = purchase.getDomainName();
            
            // Find sales with matching item name (subscription)
            List<Sale> salesByItemName = saleService.findByItemName(subscription);
            
            // Filter sales by domain name (from item description)
            for (Sale sale : salesByItemName) {
                // Check if the sale's item description contains the domain name
                if (sale.getItemDesc() != null && sale.getItemDesc().contains(domainName)) {
                    // Compare quantity
                    if (!sale.getQuantity().equals(purchase.getQuantity())) {
                        createQuantityVariationNotification(sale, purchase);
                    }
                    
                    // Compare price
                    BigDecimal salePrice = sale.getPricePerUnit();
                    BigDecimal purchasePrice = purchase.getRatePerUnit();
                    
                    if (salePrice.compareTo(purchasePrice) != 0) {
                        createPriceVariationNotification(sale, purchase, salePrice, purchasePrice);
                    }
                }
            }
        }
    }
    
    /**
     * Create a notification for quantity variation
     */
    private void createQuantityVariationNotification(Sale sale, Purchase purchase) {
        // Check if notification already exists
        List<Notification> existingNotifications = notificationService.findByItemNameAndDomainName(
                sale.getItemName(), purchase.getDomainName());
        
        // If notification for this item and domain already exists, don't create a new one
        for (Notification existing : existingNotifications) {
            if ("QUANTITY_VARIATION".equals(existing.getNotificationType())) {
                return;
            }
        }
        
        Notification notification = new Notification();
        notification.setNotificationType("QUANTITY_VARIATION");
        notification.setItemName(sale.getItemName());
        notification.setDomainName(purchase.getDomainName());
        notification.setSaleQuantity(sale.getQuantity());
        notification.setPurchaseQuantity(purchase.getQuantity());
        notification.setQuantityDifference(sale.getQuantity() - purchase.getQuantity());
        
        notificationService.save(notification);
    }
    
    /**
     * Create a notification for price variation
     */
    private void createPriceVariationNotification(Sale sale, Purchase purchase, 
                                                 BigDecimal salePrice, BigDecimal purchasePrice) {
        // Check if notification already exists
        List<Notification> existingNotifications = notificationService.findByItemNameAndDomainName(
                sale.getItemName(), purchase.getDomainName());
        
        // If notification for this item and domain already exists, don't create a new one
        for (Notification existing : existingNotifications) {
            if ("PRICE_VARIATION".equals(existing.getNotificationType())) {
                return;
            }
        }
        
        Notification notification = new Notification();
        notification.setNotificationType("PRICE_VARIATION");
        notification.setItemName(sale.getItemName());
        notification.setDomainName(purchase.getDomainName());
        notification.setSalePrice(salePrice);
        notification.setPurchasePrice(purchasePrice);
        
        // Calculate price difference
        BigDecimal priceDifference = salePrice.subtract(purchasePrice);
        notification.setPriceDifference(priceDifference);
        
        // Calculate price difference percentage
        if (purchasePrice.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal percentageDifference = priceDifference
                    .divide(purchasePrice, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
            notification.setPriceDifferencePercentage(percentageDifference);
        } else {
            notification.setPriceDifferencePercentage(BigDecimal.ZERO);
        }
        
        notificationService.save(notification);
    }
    
    /**
     * Get a summary of all variations between sales and purchases
     * @return A string with the summary of all variations
     */
    public String getVariationsSummary() {
        List<Notification> notifications = notificationService.findAll();
        
        int quantityVariations = 0;
        int priceVariations = 0;
        
        for (Notification notification : notifications) {
            if ("QUANTITY_VARIATION".equals(notification.getNotificationType())) {
                quantityVariations++;
            } else if ("PRICE_VARIATION".equals(notification.getNotificationType())) {
                priceVariations++;
            }
        }
        
        return String.format("Found %d quantity variations and %d price variations.", 
                quantityVariations, priceVariations);
    }
}