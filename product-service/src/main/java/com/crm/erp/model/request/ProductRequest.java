package com.crm.erp.model.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductRequest {
    private String id;
    private String productName;
    private String materialUsedId;
    private String category;
    private String dimension;
    private String productImageLink;
    private String polish;
    private double actualPrice;
    private double price;
    private String fabric;
    private String description;
    private String orgId;
    private int quantity;
    private LocalDate updateDateTime;
    private LocalDate createdDateTime;
}
