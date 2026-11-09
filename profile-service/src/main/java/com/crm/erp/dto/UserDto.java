package com.crm.erp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    private String id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String role;
    private String[] authorities;
    private String orgId;
}
