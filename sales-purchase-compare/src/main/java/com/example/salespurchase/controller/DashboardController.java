package com.example.salespurchase.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.salespurchase.model.Notification;
import com.example.salespurchase.service.ComparisonService;
import com.example.salespurchase.service.NotificationService;
import com.example.salespurchase.service.PurchaseService;
import com.example.salespurchase.service.SaleService;

@Controller
//@RequiredArgsConstructor
public class DashboardController {

    private final SaleService saleService;
    private final PurchaseService purchaseService;
    private final NotificationService notificationService;
    private final ComparisonService comparisonService;
    
    public DashboardController(SaleService saleService, PurchaseService purchaseService,
			NotificationService notificationService, ComparisonService comparisonService) {
		this.saleService = saleService;
		this.purchaseService = purchaseService;
		this.notificationService = notificationService;
		this.comparisonService = comparisonService;
	}

	@GetMapping("/")
    public String dashboard(Model model) {
        // Count of total sales and purchases
        long totalSales = saleService.findAll().size();
        long totalPurchases = purchaseService.findAll().size();
        
        // Get unread notifications
        List<Notification> unreadNotifications = notificationService.findByIsRead(false);
        
        // Get summary of variations
        String variationsSummary = comparisonService.getVariationsSummary();
        
        // Add attributes to model
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalPurchases", totalPurchases);
        model.addAttribute("unreadNotifications", unreadNotifications);
        model.addAttribute("unreadCount", unreadNotifications.size());
        model.addAttribute("variationsSummary", variationsSummary);
        
        return "index";
    }
}