package com.crm.erp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private String primaryContactNo;
    private LocalDate lastLoginDate;
    private LocalDate lastLoginDateDisplay;
    private String role;
    private String[] authorities;
    private String orgId;
    private boolean isActive;
    private boolean isNotLocked;
    private LocalDate updatedDateTime;
    private LocalDate createdDateTime;
    private String jwtToken;
}
