package com.crm.erp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendorResponse {

    private String id;
    private String vendorName;
    private String shopName;
    private String primaryContactNo;
    private String secondaryContactNo;
    private String address;
    private String pinCode;
    private String emailId;
    private String country;
    private String state;
    private String orgId;
    private LocalDate createdDateTime;
}
