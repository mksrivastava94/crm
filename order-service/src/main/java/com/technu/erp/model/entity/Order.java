package com.technu.erp.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection="order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
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
