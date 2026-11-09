package com.crm.erp.dto;

import lombok.Data;

@Data
public class ProfileUpdateDto {

    private String organizationName;
    private String emailId;
    private String primaryContactNo;
    private String sector;
}
