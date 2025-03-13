package com.example.salespurchase.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.salespurchase.model.Sale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Long id;
    private LocalDate invoiceDate;
    private String invoiceNumber;
    private String invoiceStatus;
    private String customerName;
    private String gstTreatment;
    private BigDecimal subtotal;
    private BigDecimal total;
    private String einvoiceStatus;
    private String itemName;
    private String itemDesc;
    private Integer quantity;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal pricePerUnit;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getGstTreatment() {
		return gstTreatment;
	}

	public void setGstTreatment(String gstTreatment) {
		this.gstTreatment = gstTreatment;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getEinvoiceStatus() {
		return einvoiceStatus;
	}

	public void setEinvoiceStatus(String einvoiceStatus) {
		this.einvoiceStatus = einvoiceStatus;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	// Convert DTO to Entity
    public Sale toEntity() {
        Sale sale = new Sale();
        sale.setId(this.id);
        sale.setInvoiceDate(this.invoiceDate);
        sale.setInvoiceNumber(this.invoiceNumber);
        sale.setInvoiceStatus(this.invoiceStatus);
        sale.setCustomerName(this.customerName);
        sale.setGstTreatment(this.gstTreatment);
        sale.setSubtotal(this.subtotal);
        sale.setTotal(this.total);
        sale.setEinvoiceStatus(this.einvoiceStatus);
        sale.setItemName(this.itemName);
        sale.setItemDesc(this.itemDesc);
        sale.setQuantity(this.quantity);
        sale.setStartDate(this.startDate);
        sale.setEndDate(this.endDate);
        return sale;
    }

    // Convert Entity to DTO
    public static SaleDTO fromEntity(Sale sale) {
        SaleDTO dto = new SaleDTO();
        dto.setId(sale.getId());
        dto.setInvoiceDate(sale.getInvoiceDate());
        dto.setInvoiceNumber(sale.getInvoiceNumber());
        dto.setInvoiceStatus(sale.getInvoiceStatus());
        dto.setCustomerName(sale.getCustomerName());
        dto.setGstTreatment(sale.getGstTreatment());
        dto.setSubtotal(sale.getSubtotal());
        dto.setTotal(sale.getTotal());
        dto.setEinvoiceStatus(sale.getEinvoiceStatus());
        dto.setItemName(sale.getItemName());
        dto.setItemDesc(sale.getItemDesc());
        dto.setQuantity(sale.getQuantity());
        dto.setStartDate(sale.getStartDate());
        dto.setEndDate(sale.getEndDate());
        dto.setPricePerUnit(sale.getPricePerUnit());
        return dto;
    }
}