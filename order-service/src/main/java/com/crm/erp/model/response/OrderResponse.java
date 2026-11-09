package com.crm.erp.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private String id;
    private String productId;
    private String productName;
    private String orderBy;
    private String clientName;
    private int quantity;
    private double advanceAmount;
    private String paymentMode;
    private String deliveryDate;
    private String status;
    private String message;
    private String orgId;

    private LocalDate createdDateTime;
    private LocalDate updatedDateTime;
}
