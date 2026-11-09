package com.crm.erp.model.response.advance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvanceAmountResponse {
    private String id;
    private double advanceAmount;
    private String paidBy;
    private String paidById;
    private LocalDate createdDateTime;
}
