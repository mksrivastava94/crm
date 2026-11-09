package com.technu.erp.model.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClientUpdateRequest {
    private String id;
    private String clientName;
    private String primaryContactNo;
    private String secondaryContactNo;
    private String emailId;
    private String address;
    private String pinCode;
    private String country;
    private String state;
    private String orgId;
    private LocalDate createdDate;
}
