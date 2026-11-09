package com.technu.erp.controller;

import java.util.List;

import com.technu.erp.exception.domain.InvalidOrgIdOrEmpty;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technu.erp.dto.PurchaseDto;
import com.technu.erp.dto.PurchaseDateDto;
import com.technu.erp.exception.ExceptionHandling;
import com.technu.erp.service.PurchaseService;

@RestController
@RequestMapping("/api/v1/purchase-product")
@AllArgsConstructor
public class PurchaseController extends ExceptionHandling {

	private final PurchaseService purchaseService;

	@PostMapping("/add")
	public ResponseEntity<PurchaseDto> createPurchase(@RequestBody PurchaseDto purchaseDto) throws InvalidOrgIdOrEmpty {
		return purchaseService.createPurchase(purchaseDto);
	}

	@GetMapping("/list/{orgId}")
	public ResponseEntity<List<PurchaseDto>> getAllPurchase(@PathVariable String orgId) {
		return purchaseService.getAllPurchase(orgId);
	}

	@GetMapping("/date/{orgId}")
	public ResponseEntity<List<PurchaseDto>> getAllPurchaseByMonth(@PathVariable String orgId, @RequestBody PurchaseDateDto purchaseDateDto) {
		return purchaseService.getAllPurchaseByMonth(orgId, purchaseDateDto);
	}
}
