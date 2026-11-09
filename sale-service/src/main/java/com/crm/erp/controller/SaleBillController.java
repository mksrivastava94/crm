package com.crm.erp.controller;

import com.crm.erp.dto.SaleBillDateRangeDto;
import com.crm.erp.dto.SaleBillDto;
import com.crm.erp.service.SaleBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sale-bill")
public class SaleBillController {

    private final SaleBillService saleBillService;

    @Autowired
    public SaleBillController(SaleBillService saleBillService) {
        this.saleBillService = saleBillService;
    }

    @PostMapping("/add")
    public ResponseEntity<SaleBillDto> createSaleBill(@RequestBody SaleBillDto saleBillDto) {
        return saleBillService.createSaleBill(saleBillDto);
    }

    @GetMapping("/list/{orgId}")
    public ResponseEntity<List<SaleBillDto>> getAllSaleBill(@PathVariable String orgId) {
        return saleBillService.getAllSaleBill(orgId);
    }

    @GetMapping("/date/{orgId}")
    public ResponseEntity<List<SaleBillDto>> getAllSaleBillByMonth(@PathVariable String orgId, @RequestBody SaleBillDateRangeDto dateRangeDto) {
        return saleBillService.getAllSaleBillByMonth(orgId, dateRangeDto);
    }
}
