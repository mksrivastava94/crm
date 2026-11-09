package com.crm.erp.util;

import com.crm.erp.model.entity.Vendor;
import com.crm.erp.model.request.VendorRequest;
import com.crm.erp.model.response.VendorResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ConversionUtil {

    private ConversionUtil() {
    }

    public static Vendor convertVendorRequestToVendor(VendorRequest request){

        return Vendor.builder()
                .vendorName(request.getVendorName())
                .emailId(request.getEmailId())
                .primaryContactNo(request.getPrimaryContactNo())
                .shopName(request.getShopName())
                .address(request.getAddress())
                .country(request.getCountry())
                .orgId(request.getOrgId())
                .pinCode(request.getPinCode())
                .state(request.getState())
                .secondaryContactNo(request.getSecondaryContactNo())
                .createdDateTime(request.getCreatedDateTime())
                .build();
    }
    public static VendorResponse convertVendorToVendorResponse(Vendor vendor){

        return VendorResponse.builder()
                .id(vendor.getId())
                .vendorName(vendor.getVendorName())
                .emailId(vendor.getEmailId())
                .primaryContactNo(vendor.getPrimaryContactNo())
                .shopName(vendor.getShopName())
                .address(vendor.getAddress())
                .country(vendor.getCountry())
                .orgId(vendor.getOrgId())
                .pinCode(vendor.getPinCode())
                .state(vendor.getState())
                .secondaryContactNo(vendor.getSecondaryContactNo())
                .createdDateTime(vendor.getCreatedDateTime())
                .build();
    }

    public static List<VendorResponse> convertVendorListToVendorResponseList(List<Vendor> list){

         return list
                 .stream()
                 .map(data -> convertVendorToVendorResponse(data))
                 .collect(Collectors.toList());
    }

}
