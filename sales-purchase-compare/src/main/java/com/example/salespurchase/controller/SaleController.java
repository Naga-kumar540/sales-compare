//package com.example.salespurchase.controller;
//
//import com.example.salespurchase.model.Sale;
//import com.example.salespurchase.service.ComparisonService;
//import com.example.salespurchase.service.SaleService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/sale")
//@RequiredArgsConstructor
//public class SaleController {
//
//    private final SaleService saleService;
//    private final ComparisonService comparisonService;
//
//    @GetMapping("/list")
//    public String listSales(Model model) {
//        List<Sale> sales = saleService.findAll();
//        model.addAttribute("sales", sales);
//        return "sale/list";
//    }
//    
//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
//        Optional<Sale> saleOpt = saleService.findById(id);
//        if (saleOpt.isPresent()) {
//            model.addAttribute("sale", saleOpt.get());
//            return "sale/form";
//        } else {
//            redirectAttributes.addFlashAttribute("error", "Sale not found with ID: " + id);
//            return "redirect:/sale/list";
//        }
//    }
//    
//    @PostMapping("/edit/{id}")
//    public String updateSale(@PathVariable Long id, 
//                            @Valid @ModelAttribute("sale") Sale sale,
//                            BindingResult result,
//                            RedirectAttributes redirectAttributes) {
//        if (result.hasErrors()) {
//            return "sale/form";
//        }
//        
//        sale.setId(id);
//        saleService.save(sale);
//        comparisonService.generateNotifications();
//        redirectAttributes.addFlashAttribute("success", "Sale successfully updated.");
//        return "redirect:/sale/list";
//    }
//    
//    @GetMapping("/delete/{id}")
//    public String deleteSale(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//        try {
//            saleService.deleteById(id);
//            redirectAttributes.addFlashAttribute("success", "Sale successfully deleted.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Failed to delete sale: " + e.getMessage());
//        }
//        return "redirect:/sale/list";
//    }
//    
//    @GetMapping("/search")
//    public String searchSales(@RequestParam(required = false) String itemName, 
//                              @RequestParam(required = false) String itemDesc,
//                              Model model) {
//        List<Sale> sales;
//        
//        if (itemName != null && !itemName.isEmpty() && itemDesc != null && !itemDesc.isEmpty()) {
//            sales = saleService.findByItemNameAndItemDescContaining(itemName, itemDesc);
//        } else if (itemName != null && !itemName.isEmpty()) {
//            sales = saleService.findByItemName(itemName);
//        } else if (itemDesc != null && !itemDesc.isEmpty()) {
//            sales = saleService.findByItemDescContaining(itemDesc);
//        } else {
//            sales = saleService.findAll();
//        }
//        
//        model.addAttribute("sales", sales);
//        model.addAttribute("itemName", itemName);
//        model.addAttribute("itemDesc", itemDesc);
//        return "sale/list";
//    }
//
//    @GetMapping("/add")
//    public String showAddForm(Model model) {
//        model.addAttribute("sale", new Sale());
//        return "sale/form";
//    }
//
//    @PostMapping("/add")
//    public String addSale(@Valid @ModelAttribute("sale") Sale sale,
//                         BindingResult result,
//                         RedirectAttributes redirectAttributes) {
//        if (result.hasErrors()) {
//            return "sale/form";
//        }
//        
//        saleService.save(sale);
//        comparisonService.generateNotifications();
//        redirectAttributes.addFlashAttribute("success", "Sale successfully added.");
//        return "redirect:/sale/list";
//    }
package com.example.salespurchase.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.salespurchase.dto.SaleDTO;
import com.example.salespurchase.exception.ResourceNotFoundException;
import com.example.salespurchase.model.Sale;
import com.example.salespurchase.service.ComparisonService;
import com.example.salespurchase.service.NotificationService;
import com.example.salespurchase.service.SaleService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/sale")
//@RequiredArgsConstructor
@Slf4j
public class SaleController {
	private static final Logger log = LoggerFactory.getLogger(SaleController.class);
    private final SaleService saleService;
    private final ComparisonService comparisonService;
    private final NotificationService notificationService;
    
    public SaleController(SaleService saleService, ComparisonService comparisonService,
			NotificationService notificationService) {
		super();
		this.saleService = saleService;
		this.comparisonService = comparisonService;
		this.notificationService = notificationService;
	}

	@GetMapping("/list")
    public String listSales(Model model) {
        try {
            List<Sale> sales = saleService.findAll();
            model.addAttribute("sales", sales);
            model.addAttribute("unreadCount", notificationService.countUnreadNotifications());
            return "sale/list";
        } catch (Exception e) {
            log.error("Error getting sales list", e);
            model.addAttribute("error", "Failed to retrieve sales: " + e.getMessage());
            return "sale/list";
        }
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        // Pre-populate with current date if it's a new sale
        Sale sale = new Sale();
        sale.setInvoiceDate(LocalDate.now());
        model.addAttribute("sale", sale);
        model.addAttribute("isNew", true);
        return "sale/form";
    }

    @PostMapping("/add")
    public String addSale(@Valid @ModelAttribute("sale") Sale sale,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "sale/form";
        }
        
        try {
            Sale savedSale = saleService.save(sale);
            comparisonService.generateNotifications();
            redirectAttributes.addFlashAttribute("success", "Sale successfully added with ID: " + savedSale.getId());
            return "redirect:/sale/list";
        } catch (DataIntegrityViolationException e) {
            result.addError(new FieldError("sale", "invoiceNumber", "Invoice number already exists"));
            return "sale/form";
        } catch (Exception e) {
            log.error("Error saving sale", e);
            redirectAttributes.addFlashAttribute("error", "Failed to save sale: " + e.getMessage());
            return "redirect:/sale/add";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Optional<Sale> saleOpt = saleService.findById(id);
            if (saleOpt.isPresent()) {
                model.addAttribute("sale", saleOpt.get());
                model.addAttribute("isNew", false);
                return "sale/form";
            } else {
                throw new ResourceNotFoundException("Sale not found with ID: " + id);
            }
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/sale/list";
        } catch (Exception e) {
            log.error("Error fetching sale for edit", e);
            redirectAttributes.addFlashAttribute("error", "Error fetching sale: " + e.getMessage());
            return "redirect:/sale/list";
        }
    }
    
    @PostMapping("/edit/{id}")
    public String updateSale(@PathVariable Long id, 
                            @Valid @ModelAttribute("sale") Sale sale,
                            BindingResult result,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "sale/form";
        }
        
        try {
            // Ensure the ID matches
            sale.setId(id);
            Sale updatedSale = saleService.save(sale);
            comparisonService.generateNotifications();
            redirectAttributes.addFlashAttribute("success", "Sale successfully updated with ID: " + updatedSale.getId());
            return "redirect:/sale/list";
        } catch (Exception e) {
            log.error("Error updating sale", e);
            redirectAttributes.addFlashAttribute("error", "Failed to update sale: " + e.getMessage());
            return "redirect:/sale/edit/" + id;
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deleteSale(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            saleService.deleteById(id);
            comparisonService.generateNotifications(); // Re-evaluate notifications after deletion
            redirectAttributes.addFlashAttribute("success", "Sale successfully deleted with ID: " + id);
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            log.error("Error deleting sale", e);
            redirectAttributes.addFlashAttribute("error", "Failed to delete sale: " + e.getMessage());
        }
        return "redirect:/sale/list";
    }
    
    @GetMapping("/search")
    public String searchSales(@RequestParam(required = false) String itemName, 
                              @RequestParam(required = false) String itemDesc,
                              @RequestParam(required = false) String startDate,
                              @RequestParam(required = false) String endDate,
                              Model model) {
        try {
            List<Sale> sales;
            
            // Parse dates if provided
            LocalDate parsedStartDate = null;
            LocalDate parsedEndDate = null;
            
            if (startDate != null && !startDate.isEmpty()) {
                parsedStartDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
            }
            
            if (endDate != null && !endDate.isEmpty()) {
                parsedEndDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
            }
            
            // Advanced search with multiple criteria
            sales = saleService.findBySearchCriteria(itemName, itemDesc, parsedStartDate, parsedEndDate);
            
            model.addAttribute("sales", sales);
            model.addAttribute("itemName", itemName);
            model.addAttribute("itemDesc", itemDesc);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            model.addAttribute("unreadCount", notificationService.countUnreadNotifications());
            return "sale/list";
        } catch (Exception e) {
            log.error("Error searching sales", e);
            model.addAttribute("error", "Search failed: " + e.getMessage());
            return "sale/list";
        }
    }
    
    @GetMapping("/details/{id}")
    @ResponseBody
    public ResponseEntity<?> getSaleDetails(@PathVariable Long id) {
        try {
            Optional<Sale> saleOpt = saleService.findById(id);
            if (saleOpt.isPresent()) {
                SaleDTO saleDTO = SaleDTO.fromEntity(saleOpt.get());
                return ResponseEntity.ok(saleDTO);
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Sale not found with ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            log.error("Error fetching sale details", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @GetMapping("/count")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getSaleCount() {
        try {
            long count = saleService.findAll().size();
            Map<String, Object> response = new HashMap<>();
            response.put("count", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting sale count", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    // Advanced search method for the service
    @GetMapping("/search/advanced")
    public String advancedSearch(Model model) {
        model.addAttribute("isAdvancedSearch", true);
        return "sale/advanced-search";
    }
}