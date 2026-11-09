package com.technu.erp.model.request.advance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvanceAmountRequest {

    private String empId;
    private double advanceAmount;
    private String paidBy;
    private int month;
    private int year;
    private LocalDate createdDateTime;
    private String orgId;
}
