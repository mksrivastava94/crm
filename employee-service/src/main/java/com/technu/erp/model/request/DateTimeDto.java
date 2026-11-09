package com.technu.erp.model.request;

import lombok.*;

import java.time.LocalDate;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DateTimeDto {

    private int month;
    private int year;
    private LocalDate startDate;
    private LocalDate endDate;
}
