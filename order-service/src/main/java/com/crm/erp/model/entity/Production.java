package com.crm.erp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection="production")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Production {

    @Id
    private String id;
    private String orderId;
    private String buildBy;
    private double timeTaken;
    private String status;
    private String message;
    private String orgId;

    private LocalDate updatedDateTime = LocalDate.now();
    private LocalDate createdDateTime = LocalDate.now();
}