package com.technu.erp.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class ProfileDateDto {
    private int month;
    private int year;
    private LocalDate startDate;
    private LocalDate endDate;
}
