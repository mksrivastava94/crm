package com.crm.erp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection="vendor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vendor {

    @Id
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
