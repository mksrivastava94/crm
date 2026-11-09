package com.crm.erp.controller;

import com.crm.erp.exception.domain.EmployeeNotFoundException;
import com.crm.erp.model.request.advance.AdvanceAmountRequest;
import com.crm.erp.model.response.advance.AdvanceAmountResponse;
import com.crm.erp.service.AdvanceAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/advance")
public class AdvanceAmountController {

    private final AdvanceAmountService advanceAmountService;

    @Autowired
    public AdvanceAmountController(AdvanceAmountService advanceAmountService) {
        this.advanceAmountService = advanceAmountService;
    }

    @PostMapping("/paid-advance")
    public ResponseEntity<String> paidAdvance(@RequestBody AdvanceAmountRequest request) throws EmployeeNotFoundException {
        return advanceAmountService.paidAdvance(request);
    }

//    @PatchMapping("/paid-advance/{id}")
//    public ResponseEntity<String> paidBalance(@PathVariable String id, @RequestBody AdvancePaymentDto advancePaymentDto){
//        return advanceAmountService.paidBalance(id,advancePaymentDto);
//    }

    @GetMapping("/list/{orgId}/{empId}")
    public ResponseEntity<List<AdvanceAmountResponse>> getAllAdvanceByEmployee(@PathVariable String orgId, @PathVariable String empId) throws EmployeeNotFoundException {
        return advanceAmountService.getAllAdvanceByEmployee(orgId,empId);
    }
}
