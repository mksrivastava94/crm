package com.technu.erp.model;

import lombok.*;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="product_purchase_bill")
@Data
@ToString
public class Purchase {

    @Id
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

