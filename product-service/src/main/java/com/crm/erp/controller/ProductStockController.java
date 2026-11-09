package com.crm.erp.controller;

import com.crm.erp.model.request.ProductUpdateRequest;
import com.crm.erp.exception.ExceptionHandling;
import com.crm.erp.exception.domain.ProductNotFoundException;
import com.crm.erp.service.ProductStockService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product-stock")
@AllArgsConstructor
public class ProductStockController extends ExceptionHandling {

    private final ProductStockService productStockService;

    @PutMapping("/update/stock/{id}")
    public ResponseEntity<Integer> updateStock(@PathVariable String id, @RequestBody ProductUpdateRequest request) throws ProductNotFoundException {
        return productStockService.updateStock(id,request);
    }

}
