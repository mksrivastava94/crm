package com.crm.erp.controller;

import com.crm.erp.model.request.salary.SalaryRequest;
import com.crm.erp.exception.ExceptionHandling;
import com.crm.erp.exception.domain.EmployeeNotFoundException;
import com.crm.erp.model.response.salary.SalaryResponse;
import com.crm.erp.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salary")
public class SalaryController extends ExceptionHandling {

    private final SalaryService salaryService;

    @Autowired
    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @PostMapping("/paid")
    public ResponseEntity<String> paidSalary(@RequestBody SalaryRequest request) throws EmployeeNotFoundException {
        return salaryService.paidSalary(request);
    }

    @GetMapping("/list/{orgId}/{empId}")
    public ResponseEntity<List<SalaryResponse>> getAllSalaryByEmployee(@PathVariable String orgId, @PathVariable String empId) throws EmployeeNotFoundException {
        return salaryService.getAllSalaryByEmployee(orgId,empId);
    }
}
