package com.technu.erp.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class SaleBillDto {

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
