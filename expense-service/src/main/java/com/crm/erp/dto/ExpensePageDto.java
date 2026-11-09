package com.crm.erp.dto;

import lombok.Data;
import java.util.List;

@Data
public class ExpensePageDto {

    private List<ExpenseDto> data;
    private int totalPages;
}
