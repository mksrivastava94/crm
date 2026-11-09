package com.technu.erp.model.entity;

import com.technu.erp.constant.EmployeeConstant;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Document(collection = "salary")
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Salary {

        @Id
        private String id;

        @NotEmpty(message = EmployeeConstant.EMPLOYEE_NAME_NOT_EMPTY)
        private String empId;

        private double noOfDays;
        private double expenseAmount;
        private double totalSalary;
        private String paidBy;
        private String paymentMode;
        private double advanceReturn;
        private LocalDate createdDateTime;

        @NotEmpty(message = "Organization name is incorrect")
        private String orgId;
}
