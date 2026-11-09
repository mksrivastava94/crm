package com.crm.erp.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientRequest {

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


