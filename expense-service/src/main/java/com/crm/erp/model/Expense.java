package com.crm.erp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection="expense")
@Data
public class Expense {

    @Id
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
