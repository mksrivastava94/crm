package com.crm.erp.model.entity;

import com.crm.erp.constant.EmployeeConstant;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Document(collection = "advance_payment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvancePayment {

    @Id
    private String id;

    @NotEmpty(message = EmployeeConstant.EMPLOYEE_NAME_NOT_EMPTY)
    private String empId;
    private double advanceAmount;
    private String paidBy;
    private String paidById;
    private int month;
    private int year;
    private LocalDate createdDateTime;
    private String orgId;
}
