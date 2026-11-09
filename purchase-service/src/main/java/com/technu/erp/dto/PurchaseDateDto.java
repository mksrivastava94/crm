package com.technu.erp.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseDateDto {

	private int month;
	private int year;
	private LocalDate startDate;
	private LocalDate endDate;
}
