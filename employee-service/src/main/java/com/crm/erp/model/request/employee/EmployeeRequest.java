package com.crm.erp.model.request.employee;


import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private String fullName;
    private String fatherName;
    private String designation;
    private double perDayWages;
    private String primaryContactNo;
    private String secondaryContactNo;
    private String imageLink;
    private String emailId;
    private String aadharCardNo;
    private String panCardNo;
    private String address;
    private String pinCode;
    private String country;
    private String state;
    private String orgId;
    private boolean status;
    private LocalDate createdDateTime;

}
