package com.technu.erp.controller;

import com.technu.erp.model.request.ProductUpdateRequest;
import com.technu.erp.exception.ExceptionHandling;
import com.technu.erp.exception.domain.ProductNotFoundException;
import com.technu.erp.service.ProductStockService;
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
