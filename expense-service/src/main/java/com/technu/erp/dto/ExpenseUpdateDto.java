package com.technu.erp.dto;

import lombok.Data;

@Data
public class ExpenseUpdateDto {

    private String orderId;
    private String invoiceLink;
    private double amount;
    private double amountPaid;
    private double balance;

}
