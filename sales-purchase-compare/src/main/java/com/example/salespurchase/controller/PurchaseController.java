package com.example.salespurchase.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.salespurchase.model.Purchase;
import com.example.salespurchase.service.ComparisonService;
import com.example.salespurchase.service.PurchaseService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/purchase")
//@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final ComparisonService comparisonService;
    
    public PurchaseController(PurchaseService purchaseService, ComparisonService comparisonService) {
	
		this.purchaseService = purchaseService;
		this.comparisonService = comparisonService;
	}

	@GetMapping("/list")
    public String listPurchases(Model model) {
        List<Purchase> purchases = purchaseService.findAll();
        model.addAttribute("purchases", purchases);
        return "purchase/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("purchase", new Purchase());
        return "purchase/form";
    }

    @PostMapping("/add")
    public String addPurchase(@Valid @ModelAttribute("purchase") Purchase purchase,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "purchase/form";
        }
        
        purchaseService.save(purchase);
        comparisonService.generateNotifications();
        redirectAttributes.addFlashAttribute("success", "Purchase successfully added.");
        return "redirect:/purchase/list";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Purchase> purchaseOpt = purchaseService.findById(id);
        if (purchaseOpt.isPresent()) {
            model.addAttribute("purchase", purchaseOpt.get());
            return "purchase/form";
        } else {
            redirectAttributes.addFlashAttribute("error", "Purchase not found with ID: " + id);
            return "redirect:/purchase/list";
        }
    }
    
    @PostMapping("/edit/{id}")
    public String updatePurchase(@PathVariable Long id, 
                                @Valid @ModelAttribute("purchase") Purchase purchase,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "purchase/form";
        }
        
        purchase.setId(id);
        purchaseService.save(purchase);
        comparisonService.generateNotifications();
        redirectAttributes.addFlashAttribute("success", "Purchase successfully updated.");
        return "redirect:/purchase/list";
    }
    
    @GetMapping("/delete/{id}")
    public String deletePurchase(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            purchaseService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Purchase successfully deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete purchase: " + e.getMessage());
        }
        return "redirect:/purchase/list";
    }
    
    @GetMapping("/search")
    public String searchPurchases(@RequestParam(required = false) String subscription, 
                                 @RequestParam(required = false) String domainName,
                                 Model model) {
        List<Purchase> purchases;
        
        if (subscription != null && !subscription.isEmpty() && domainName != null && !domainName.isEmpty()) {
            purchases = purchaseService.findBySubscriptionAndDomainName(subscription, domainName);
        } else if (subscription != null && !subscription.isEmpty()) {
            purchases = purchaseService.findBySubscription(subscription);
        } else if (domainName != null && !domainName.isEmpty()) {
            purchases = purchaseService.findByDomainName(domainName);
        } else {
            purchases = purchaseService.findAll();
        }
        
        model.addAttribute("purchases", purchases);
        model.addAttribute("subscription", subscription);
        model.addAttribute("domainName", domainName);
        return "purchase/list";
    }
}