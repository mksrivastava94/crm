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
public class ClientResponse {

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


