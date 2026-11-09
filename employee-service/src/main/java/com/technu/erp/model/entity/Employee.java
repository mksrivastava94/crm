package com.technu.erp.model.entity;


import com.technu.erp.constant.CommonConstant;
import com.technu.erp.constant.EmployeeConstant;
import com.technu.erp.util.DateTimeUtil;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Document(collection="employee")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    private String id;
    @NotEmpty(message = EmployeeConstant.EMPLOYEE_NAME_NOT_EMPTY)
    private String fullName;
    
    private String fatherName;
    private String designation;
    @NotNull(message = EmployeeConstant.PER_DAY_WAGES)
    @DecimalMin(value = "0.0")
    private double perDayWages;

    @Length(min = 10,max = 10,message = CommonConstant.ENTER_VALID_PHONE_NO)
    private String primaryContactNo;

    private String secondaryContactNo;
    private String imageLink;
    private String emailId;

    @NotEmpty(message = EmployeeConstant.AADHAR_CARD_NOT_EMPTY)
    private String aadharCardNo;

    @NotEmpty(message = EmployeeConstant.PAN_CARD_NOT_EMPTY)
    private String panCardNo;

    private String address;
    private String pinCode;
    private String country;
    private String state;
    private String orgId;
    private double totalAdvance;

    private boolean status;
    private LocalDate createdDateTime;
}
