package com.example.salespurchase.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.salespurchase.model.Purchase;
import com.example.salespurchase.model.Sale;
import com.example.salespurchase.service.ExcelService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/excel")
//@RequiredArgsConstructor
public class ExcelController {

    private final ExcelService excelService;
    
    public ExcelController(ExcelService excelService) {
		this.excelService = excelService;
	}

	@GetMapping("/upload")
    public String showUploadForm() {
        return "excel/upload";
    }

    @PostMapping("/upload/sales")
    public String uploadSalesExcel(@RequestParam("file") MultipartFile file,
                                  RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please select a file to upload.");
            return "redirect:/excel/upload";
        }

        try {
            List<Sale> sales = excelService.processSalesExcel(file);
            redirectAttributes.addFlashAttribute("success", 
                    String.format("Successfully uploaded and processed %d sales records.", sales.size()));
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", 
                    "Failed to process the file: " + e.getMessage());
        }

        return "redirect:/sale/list";
    }

    @PostMapping("/upload/purchases")
    public String uploadPurchasesExcel(@RequestParam("file") MultipartFile file,
                                      RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please select a file to upload.");
            return "redirect:/excel/upload";
        }

        try {
            List<Purchase> purchases = excelService.processPurchasesExcel(file);
            redirectAttributes.addFlashAttribute("success", 
                    String.format("Successfully uploaded and processed %d purchase records.", purchases.size()));
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", 
                    "Failed to process the file: " + e.getMessage());
        }

        return "redirect:/purchase/list";
    }

    @GetMapping("/download/sales")
    public void downloadSalesExcel(HttpServletResponse response) throws IOException {
        List<Sale> sales = null; // Get sales data from service
        Workbook workbook = excelService.generateSalesExcel(sales);
        
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=sales.xlsx");
        
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        }
    }

    @GetMapping("/download/purchases")
    public void downloadPurchasesExcel(HttpServletResponse response) throws IOException {
        List<Purchase> purchases = null; // Get purchases data from service
        Workbook workbook = excelService.generatePurchasesExcel(purchases);
        
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=purchases.xlsx");
        
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        }
    }
}