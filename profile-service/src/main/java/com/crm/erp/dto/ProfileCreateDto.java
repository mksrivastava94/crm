package com.crm.erp.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProfileCreateDto {

    private String id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String organizationName;
    private String primaryContactNo;
    private String sector;
}
