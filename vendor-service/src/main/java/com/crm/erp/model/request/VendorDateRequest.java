package com.crm.erp.model.request;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class VendorDateRequest {
    private int month;
    private int year;
    private LocalDate startDate;
    private LocalDate endDate;
}
