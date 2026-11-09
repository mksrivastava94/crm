package com.crm.erp.model.response.salary;

import com.crm.erp.constant.EmployeeConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryResponse {

    private String id;
    private double noOfDays;
    private double expenseAmount;
    private double totalSalary;
    private double perDayWages;
    private String paidBy;
    private String paymentMode;
    private double advanceReturn;
    private LocalDate createdDateTime;

    @NotEmpty(message = "Organization name is incorrect")
    private String orgId;
}
