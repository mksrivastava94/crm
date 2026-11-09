package com.crm.erp.util;

import com.crm.erp.model.request.employee.EmpUpdateRequest;
import com.crm.erp.model.request.employee.EmployeeRequest;
import com.crm.erp.model.request.advance.AdvanceAmountRequest;
import com.crm.erp.model.request.salary.SalaryRequest;
import com.crm.erp.model.entity.AdvancePayment;
import com.crm.erp.model.entity.Employee;
import com.crm.erp.model.entity.Salary;
import com.crm.erp.model.response.advance.AdvanceAmountResponse;
import com.crm.erp.model.response.employee.EmployeeResponse;
import com.crm.erp.model.response.salary.SalaryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParseUtil {

    public AdvancePayment prepareAdvanceAmountEntity(AdvanceAmountRequest request,String paidBy) {
        return AdvancePayment.builder()
                .empId(request.getEmpId())
                .advanceAmount(request.getAdvanceAmount())
                .paidBy(paidBy)
                .paidById(request.getPaidBy())
                .createdDateTime(LocalDate.now())
                .month(LocalDate.now().getMonthValue())
                .year(LocalDate.now().getYear())
                .orgId(request.getOrgId())
                .build();
    }

    // Salary Module Parsing
    public Salary prepareSalaryEntity(SalaryRequest request, double totalSalary) {
        return Salary.builder()
                .empId(request.getEmpId())
                .paymentMode(request.getPaymentMode())
                .expenseAmount(request.getExpenseAmount())
                .noOfDays(request.getNoOfDays())
                .totalSalary(totalSalary)
                .orgId(request.getOrgId())
                .paidBy(request.getPaidBy())
                .createdDateTime(LocalDate.now())
                .build();
    }

    // Employee Parsing
    public Employee prepareEmployeeEntity(EmployeeRequest request) {
        return Employee.builder()
                .emailId(request.getEmailId())
                .fullName(request.getFullName())
                .fatherName(request.getFatherName())
                .aadharCardNo(request.getAadharCardNo())
                .panCardNo(request.getPanCardNo())
                .address(request.getAddress())
                .country(request.getCountry())
                .designation(request.getDesignation())
                .imageLink(request.getImageLink())
                .perDayWages(request.getPerDayWages())
                .orgId(request.getOrgId())
                .primaryContactNo(request.getPrimaryContactNo())
                .secondaryContactNo(request.getSecondaryContactNo())
                .state(request.getState())
                .status(true)
                .createdDateTime(LocalDate.now())
                .build();
    }

    public Employee updateRequestToEntity(EmpUpdateRequest request){
        return new ModelMapper().map(request, Employee.class);
    }


    public EmployeeResponse modelToResponse(Employee employee){
        return new ModelMapper().map(employee, EmployeeResponse.class);
    }

    public SalaryResponse modelToResponse(Salary salary){
        return new ModelMapper().map(salary, SalaryResponse.class);
    }

    public AdvanceAmountResponse modelToResponse(AdvancePayment payment){
        return new ModelMapper().map(payment, AdvanceAmountResponse.class);
    }


    public List<EmployeeResponse> empListToEmpResponseList(List<Employee> empList) {
        return empList.stream()
                .map(this::modelToResponse)
                .collect(Collectors.toList());
    }

    public List<SalaryResponse> salaryListToSalaryResponseList(List<Salary> salaryList) {
        return salaryList.stream()
                .map( this::modelToResponse)
                .collect(Collectors.toList());
    }

}
