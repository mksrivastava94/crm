package com.crm.erp.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection="sale_bill")
@Data
public class SaleBill {

    @Id
    private String id;
    private String orderId;
    private String invoiceLink;
    private double amount;
    private double amountPaid;
    private double balance;
    private String paymentStatus;
    private String orgId;
    private LocalDate createdDateTime = LocalDate.now();
}

