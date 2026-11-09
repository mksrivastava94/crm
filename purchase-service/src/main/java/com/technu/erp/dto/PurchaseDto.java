package com.technu.erp.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseDto {

	private String id;
	private String vendorId;
	private String invoiceLink;
	private double amount;
	private double amountPaid;
	private double balance;
	private String status;
	private String orgId;
	private LocalDate createdDateTime = LocalDate.now();
}
