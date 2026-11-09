package com.technu.erp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
