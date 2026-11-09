package com.crm.erp.model.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class VendorPageResponse {

    private List<VendorResponse> vendorList;
    private int totalPage;

}
