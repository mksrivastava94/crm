package com.technu.erp.model.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String organizationName;
    private String primaryContactNo;
    private String sector;
    private boolean isActive;
    private LocalDate createdDateTime;
    private LocalDate expiredDateTime;
}
