package com.crm.erp.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class ExpenseDto {

    private String id;
    private String title;
    private String orderBy;
    private String vendorName;
    private String paidBy;
    private String invoiceLink;
    private double amount;
    private double amountPaid;
    private double balance;
    private String description;
    private String orgId;
    private LocalDate createdDateTime = LocalDate.now();
}
