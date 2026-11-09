package com.technu.erp.controller;

import com.technu.erp.model.request.salary.SalaryRequest;
import com.technu.erp.exception.ExceptionHandling;
import com.technu.erp.exception.domain.EmployeeNotFoundException;
import com.technu.erp.model.response.salary.SalaryResponse;
import com.technu.erp.service.SalaryService;
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
