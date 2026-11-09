package com.technu.erp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendorRequest {

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
    private LocalDate createdDateTime = LocalDate.now();
}
