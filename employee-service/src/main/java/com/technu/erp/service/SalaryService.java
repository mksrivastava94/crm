package com.technu.erp.service;

import com.technu.erp.model.request.employee.EmployeeRequest;
import com.technu.erp.model.request.salary.SalaryRequest;
import com.technu.erp.exception.domain.EmployeeNotFoundException;
import com.technu.erp.model.entity.Salary;
import com.technu.erp.model.response.employee.EmployeeResponse;
import com.technu.erp.model.response.salary.SalaryResponse;
import com.technu.erp.repository.SalaryRepository;
import com.technu.erp.util.BasicUtil;
import com.technu.erp.util.ParseUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeService employeeService;
    private final ParseUtil parseUtil;

    @Autowired
    public SalaryService(SalaryRepository salaryRepository, EmployeeService employeeService, ParseUtil parseUtil) {
        this.salaryRepository = salaryRepository;
        this.employeeService = employeeService;
        this.parseUtil = parseUtil;
    }

    public ResponseEntity<String> paidSalary(SalaryRequest request) throws EmployeeNotFoundException {

        if(!BasicUtil.notEmptyNotNull(request.getOrgId()))
            throw new EmployeeNotFoundException("Invalid Organization Name");

        EmployeeResponse eEmployee = employeeService.getEmployeeById(request.getEmpId()).getBody();
        if(eEmployee == null)
            throw new EmployeeNotFoundException("Employee not found");

        double totalSalary = calculateSalary(eEmployee.getPerDayWages(),request);
        Salary salary = parseUtil.prepareSalaryEntity(request,totalSalary);
        salaryRepository.save(salary);
        log.info("Total salary paid : {}  to  {}",totalSalary,eEmployee.getFullName());
        return new ResponseEntity<>(("Total salary paid : "+totalSalary+"  to  "+eEmployee.getFullName()), HttpStatus.CREATED);
    }

    private double calculateSalary(double perDayWages, SalaryRequest request) {
        double monthSalary = request.getNoOfDays() * perDayWages;
        monthSalary += request.getExpenseAmount();
        return monthSalary;
    }

    public ResponseEntity<List<SalaryResponse>> getAllSalaryByEmployee(String orgId, String empId) throws EmployeeNotFoundException {
        List<Salary> salaryList = salaryRepository.findAllByOrgIdAndEmpId(orgId,empId);
        List<SalaryResponse> response =parseUtil.salaryListToSalaryResponseList(salaryList);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
