package com.technu.erp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    private String productId;
    private String orderBy;
    private int quantity;
    private double advanceAmount;
    private String paymentMode;
    private String deliveryDate;
    private String status;
    private String message;
    private String orgId;

    private LocalDate createdDateTime = LocalDate.now();
    private LocalDate updatedDateTime = LocalDate.now();
}
