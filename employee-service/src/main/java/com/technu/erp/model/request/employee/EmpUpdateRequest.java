package com.technu.erp.model.request.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpUpdateRequest {

    private String id;
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
    private double totalAdvance;
    private boolean status;
    private LocalDate createdDateTime;
}
