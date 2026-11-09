package com.technu.erp.controller;

import com.technu.erp.exception.ExceptionHandling;
import com.technu.erp.exception.domain.EmployeeExistException;
import com.technu.erp.exception.domain.EmployeeNotFoundException;
import com.technu.erp.model.request.DateTimeDto;
import com.technu.erp.model.request.employee.EmpUpdateRequest;
import com.technu.erp.model.request.employee.EmployeeRequest;
import com.technu.erp.model.request.KeyValueDto;
import com.technu.erp.model.response.employee.EmployeePageResponse;
import com.technu.erp.model.response.employee.EmployeeResponse;
import com.technu.erp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController extends ExceptionHandling {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest request) throws EmployeeExistException {
        return employeeService.createEmployee(request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable String id, @RequestBody EmpUpdateRequest request) throws EmployeeNotFoundException {
        return employeeService.updateEmployee(id,request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEmployeeById(@PathVariable String id) throws EmployeeNotFoundException {
        employeeService.deleteEmployeeById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable String id) throws EmployeeNotFoundException {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/list/{orgId}/{page}/{pageSize}")
    public ResponseEntity<EmployeePageResponse> getAllEmployee(@PathVariable String orgId, @PathVariable int page, @PathVariable int pageSize){
        return employeeService.getAllEmployee(orgId,page,pageSize);
    }

    @GetMapping("/date/{orgId}")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployeeByMonth(@PathVariable String orgId, @RequestBody DateTimeDto clientDateDto){
        return employeeService.getAllEmployeeByMonth(orgId,clientDateDto);
    }

    @GetMapping("/list/key-value/{orgId}")
    public ResponseEntity<List<KeyValueDto>> getAllEmployeeKeyValue(@PathVariable String orgId){
        return employeeService.getAllEmployeeKeyValue(orgId);
    }

}
