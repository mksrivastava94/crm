package com.crm.erp.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderDateRequest {
    private int month;
    private int year;
    private LocalDate startDate;
    private LocalDate endDate;
}
