package com.technu.erp.model.request.salary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryRequest {

    private String empId;
    private String empName;
    private double noOfDays;
    private double expenseAmount;
    private String paidBy;
    private String paymentMode;
    private LocalDate createdDateTime;
    private String orgId;
}
