package com.example.salespurchase.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.salespurchase.model.Purchase;
import com.example.salespurchase.model.Sale;

@Service
//@RequiredArgsConstructor
public class ExcelService {

    private final SaleService saleService;
    
    private final PurchaseService purchaseService;
    private final ComparisonService comparisonService;
    

    public ExcelService(SaleService saleService, PurchaseService purchaseService, ComparisonService comparisonService) {
		this.saleService = saleService;
		this.purchaseService = purchaseService;
		this.comparisonService = comparisonService;
	}

	public List<Sale> processSalesExcel(MultipartFile file) throws IOException {
        List<Sale> sales = new ArrayList<>();
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            
            // Skip header row
            if (rows.hasNext()) {
                rows.next();
            }
            
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Sale sale = new Sale();
                
                // Process each cell
                if (currentRow.getCell(0) != null) {
                    Cell dateCell = currentRow.getCell(0);
                    if (dateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(dateCell)) {
                        sale.setInvoiceDate(dateCell.getDateCellValue().toInstant()
                                .atZone(ZoneId.systemDefault()).toLocalDate());
                    }
                }
                
                if (currentRow.getCell(1) != null) {
                    sale.setInvoiceNumber(getStringValue(currentRow.getCell(1)));
                }
                
                if (currentRow.getCell(2) != null) {
                    sale.setInvoiceStatus(getStringValue(currentRow.getCell(2)));
                }
                
                if (currentRow.getCell(3) != null) {
                    sale.setCustomerName(getStringValue(currentRow.getCell(3)));
                }
                
                if (currentRow.getCell(4) != null) {
                    sale.setGstTreatment(getStringValue(currentRow.getCell(4)));
                }
                
                if (currentRow.getCell(5) != null) {
                    sale.setSubtotal(getNumericValue(currentRow.getCell(5)));
                }
                
                if (currentRow.getCell(6) != null) {
                    sale.setTotal(getNumericValue(currentRow.getCell(6)));
                }
                
                if (currentRow.getCell(7) != null) {
                    sale.setEinvoiceStatus(getStringValue(currentRow.getCell(7)));
                }
                
                if (currentRow.getCell(8) != null) {
                    sale.setItemName(getStringValue(currentRow.getCell(8)));
                }
                
                if (currentRow.getCell(9) != null) {
                    sale.setItemDesc(getStringValue(currentRow.getCell(9)));
                }
                
                if (currentRow.getCell(10) != null) {
                    Cell quantityCell = currentRow.getCell(10);
                    if (quantityCell.getCellType() == CellType.NUMERIC) {
                        sale.setQuantity((int) quantityCell.getNumericCellValue());
                    }
                }
                
                if (currentRow.getCell(11) != null) {
                    Cell startDateCell = currentRow.getCell(11);
                    if (startDateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(startDateCell)) {
                        sale.setStartDate(startDateCell.getDateCellValue().toInstant()
                                .atZone(ZoneId.systemDefault()).toLocalDate());
                    }
                }
                
                if (currentRow.getCell(12) != null) {
                    Cell endDateCell = currentRow.getCell(12);
                    if (endDateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(endDateCell)) {
                        sale.setEndDate(endDateCell.getDateCellValue().toInstant()
                                .atZone(ZoneId.systemDefault()).toLocalDate());
                    }
                }
                
                sales.add(sale);
            }
        }
        
        // Save all sales
        List<Sale> savedSales = saleService.saveAll(sales);
        
        // Generate comparisons and notifications
        comparisonService.generateNotifications();
        
        return savedSales;
    }
    
    public List<Purchase> processPurchasesExcel(MultipartFile file) throws IOException {
        List<Purchase> purchases = new ArrayList<>();
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
             
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            
            // Skip header row
            if (rows.hasNext()) {
                rows.next();
            }
            
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Purchase purchase = new Purchase();
                
                // Process each cell
                if (currentRow.getCell(0) != null) {
                    purchase.setConsole(getStringValue(currentRow.getCell(0)));
                }
                
                if (currentRow.getCell(1) != null) {
                    purchase.setDomainName(getStringValue(currentRow.getCell(1)));
                }
                
                if (currentRow.getCell(2) != null) {
                    purchase.setSubscription(getStringValue(currentRow.getCell(2)));
                }
                
                if (currentRow.getCell(3) != null) {
                    purchase.setDescription(getStringValue(currentRow.getCell(3)));
                }
                
                if (currentRow.getCell(4) != null) {
                    purchase.setOrderName(getStringValue(currentRow.getCell(4)));
                }
                
                if (currentRow.getCell(5) != null) {
                    Cell startDateCell = currentRow.getCell(5);
                    if (startDateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(startDateCell)) {
                        purchase.setStartDate(startDateCell.getDateCellValue().toInstant()
                                .atZone(ZoneId.systemDefault()).toLocalDate());
                    }
                }
                
                if (currentRow.getCell(6) != null) {
                    Cell endDateCell = currentRow.getCell(6);
                    if (endDateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(endDateCell)) {
                        purchase.setEndDate(endDateCell.getDateCellValue().toInstant()
                                .atZone(ZoneId.systemDefault()).toLocalDate());
                    }
                }
                
                if (currentRow.getCell(7) != null) {
                    Cell quantityCell = currentRow.getCell(7);
                    if (quantityCell.getCellType() == CellType.NUMERIC) {
                        purchase.setQuantity((int) quantityCell.getNumericCellValue());
                    }
                }
                
                if (currentRow.getCell(8) != null) {
                    purchase.setPoNumber(getStringValue(currentRow.getCell(8)));
                }
                
                if (currentRow.getCell(9) != null) {
                    purchase.setAmount(getNumericValue(currentRow.getCell(9)));
                }
                
                if (currentRow.getCell(10) != null) {
                    purchase.setCustomerId(getStringValue(currentRow.getCell(10)));
                }
                
                if (currentRow.getCell(11) != null) {
                    purchase.setSkuId(getStringValue(currentRow.getCell(11)));
                }
                
                if (currentRow.getCell(12) != null) {
                    purchase.setRatePerUnit(getNumericValue(currentRow.getCell(12)));
                }
                
                purchases.add(purchase);
            }
        }
        
        // Save all purchases
        List<Purchase> savedPurchases = purchaseService.saveAll(purchases);
        
        // Generate comparisons and notifications
        comparisonService.generateNotifications();
        
        return savedPurchases;
    }
    
    // Helper methods to handle different cell types
    private String getStringValue(Cell cell) {
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        } else {
            return "";
        }
    }
    
    private BigDecimal getNumericValue(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return BigDecimal.valueOf(cell.getNumericCellValue());
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return new BigDecimal(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                return BigDecimal.ZERO;
            }
        } else {
            return BigDecimal.ZERO;
        }
    }
    
    // Method to generate an Excel file with sales data
    public Workbook generateSalesExcel(List<Sale> sales) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sales");
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Invoice Date");
        headerRow.createCell(1).setCellValue("Invoice Number");
        headerRow.createCell(2).setCellValue("Invoice Status");
        headerRow.createCell(3).setCellValue("Customer Name");
        headerRow.createCell(4).setCellValue("GST Treatment");
        headerRow.createCell(5).setCellValue("SubTotal");
        headerRow.createCell(6).setCellValue("Total");
        headerRow.createCell(7).setCellValue("e-Invoice Status");
        headerRow.createCell(8).setCellValue("Item Name");
        headerRow.createCell(9).setCellValue("Item Desc");
        headerRow.createCell(10).setCellValue("Quantity");
        headerRow.createCell(11).setCellValue("Start Date");
        headerRow.createCell(12).setCellValue("End Date");
        
        // Fill data rows
        int rowNum = 1;
        for (Sale sale : sales) {
            Row row = sheet.createRow(rowNum++);
            
            // Set cell values from sale object
            if (sale.getInvoiceDate() != null) {
                Cell cell = row.createCell(0);
                cell.setCellValue(sale.getInvoiceDate());
                CellStyle dateStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
                cell.setCellStyle(dateStyle);
            }
            
            if (sale.getInvoiceNumber() != null) {
                row.createCell(1).setCellValue(sale.getInvoiceNumber());
            }
            
            if (sale.getInvoiceStatus() != null) {
                row.createCell(2).setCellValue(sale.getInvoiceStatus());
            }
            
            if (sale.getCustomerName() != null) {
                row.createCell(3).setCellValue(sale.getCustomerName());
            }
            
            if (sale.getGstTreatment() != null) {
                row.createCell(4).setCellValue(sale.getGstTreatment());
            }
            
            if (sale.getSubtotal() != null) {
                row.createCell(5).setCellValue(sale.getSubtotal().doubleValue());
            }
            
            if (sale.getTotal() != null) {
                row.createCell(6).setCellValue(sale.getTotal().doubleValue());
            }
            
            if (sale.getEinvoiceStatus() != null) {
                row.createCell(7).setCellValue(sale.getEinvoiceStatus());
            }
            
            if (sale.getItemName() != null) {
                row.createCell(8).setCellValue(sale.getItemName());
            }
            
            if (sale.getItemDesc() != null) {
                row.createCell(9).setCellValue(sale.getItemDesc());
            }
            
            if (sale.getQuantity() != null) {
                row.createCell(10).setCellValue(sale.getQuantity());
            }
            
            if (sale.getStartDate() != null) {
                Cell cell = row.createCell(11);
                cell.setCellValue(sale.getStartDate());
                CellStyle dateStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
                cell.setCellStyle(dateStyle);
            }
            
            if (sale.getEndDate() != null) {
                Cell cell = row.createCell(12);
                cell.setCellValue(sale.getEndDate());
                CellStyle dateStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
                cell.setCellStyle(dateStyle);
            }
        }
        
        // Auto-size columns
        for (int i = 0; i < 13; i++) {
            sheet.autoSizeColumn(i);
        }
        
        return workbook;
    }
    
    // Method to generate an Excel file with purchase data
    public Workbook generatePurchasesExcel(List<Purchase> purchases) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Purchases");
        
        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Console");
        headerRow.createCell(1).setCellValue("Domain Name");
        headerRow.createCell(2).setCellValue("Subscription");
        headerRow.createCell(3).setCellValue("Description");
        headerRow.createCell(4).setCellValue("Order Name");
        headerRow.createCell(5).setCellValue("Start Date");
        headerRow.createCell(6).setCellValue("End Date");
        headerRow.createCell(7).setCellValue("Quantity");
        headerRow.createCell(8).setCellValue("PO Number");
        headerRow.createCell(9).setCellValue("Amount");
        headerRow.createCell(10).setCellValue("Customer ID");
        headerRow.createCell(11).setCellValue("SKU ID");
        headerRow.createCell(12).setCellValue("Rate PU");
        
        // Fill data rows
        int rowNum = 1;
        for (Purchase purchase : purchases) {
            Row row = sheet.createRow(rowNum++);
            
            // Set cell values from purchase object
            if (purchase.getConsole() != null) {
                row.createCell(0).setCellValue(purchase.getConsole());
            }
            
            if (purchase.getDomainName() != null) {
                row.createCell(1).setCellValue(purchase.getDomainName());
            }
            
            if (purchase.getSubscription() != null) {
                row.createCell(2).setCellValue(purchase.getSubscription());
            }
            
            if (purchase.getDescription() != null) {
                row.createCell(3).setCellValue(purchase.getDescription());
            }
            
            if (purchase.getOrderName() != null) {
                row.createCell(4).setCellValue(purchase.getOrderName());
            }
            
            if (purchase.getStartDate() != null) {
                Cell cell = row.createCell(5);
                cell.setCellValue(purchase.getStartDate());
                CellStyle dateStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
                cell.setCellStyle(dateStyle);
            }
            
            if (purchase.getEndDate() != null) {
                Cell cell = row.createCell(6);
                cell.setCellValue(purchase.getEndDate());
                CellStyle dateStyle = workbook.createCellStyle();
                CreationHelper createHelper = workbook.getCreationHelper();
                dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
                cell.setCellStyle(dateStyle);
            }
            
            if (purchase.getQuantity() != null) {
                row.createCell(7).setCellValue(purchase.getQuantity());
            }
            
            if (purchase.getPoNumber() != null) {
                row.createCell(8).setCellValue(purchase.getPoNumber());
            }
            
            if (purchase.getAmount() != null) {
                row.createCell(9).setCellValue(purchase.getAmount().doubleValue());
            }
            
            if (purchase.getCustomerId() != null) {
                row.createCell(10).setCellValue(purchase.getCustomerId());
            }
            
            if (purchase.getSkuId() != null) {
                row.createCell(11).setCellValue(purchase.getSkuId());
            }
            
            if (purchase.getRatePerUnit() != null) {
                row.createCell(12).setCellValue(purchase.getRatePerUnit().doubleValue());
            }
        }
        
        // Auto-size columns
        for (int i = 0; i < 13; i++) {
            sheet.autoSizeColumn(i);
        }
        
        return workbook;
    }
}