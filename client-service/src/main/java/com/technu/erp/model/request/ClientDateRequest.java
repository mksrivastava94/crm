package com.technu.erp.model.request;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
public class ClientDateRequest {
    private int month;
    private int year;
    private LocalDate startDate;
    private LocalDate endDate;
}
