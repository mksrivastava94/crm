package com.crm.erp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDto {

    private String id;
    private String category;
    private String materialName;
    private String materialImageLink;
    private String description;
    private double pricePerUnit;
    private String unitType;
    private Integer quantity;
    private Integer quantityUsed;
    private String dimension;
    private String orgId;
    private LocalDate updateDate = LocalDate.now();
    private LocalDate createdDate = LocalDate.now();
}
