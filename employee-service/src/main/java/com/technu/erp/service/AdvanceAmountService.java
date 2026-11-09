package com.technu.erp.service;

import com.technu.erp.exception.domain.EmployeeNotFoundException;
import com.technu.erp.model.entity.AdvancePayment;
import com.technu.erp.model.request.advance.AdvanceAmountRequest;
import com.technu.erp.model.response.advance.AdvanceAmountResponse;
import com.technu.erp.repository.AdvanceAmountRepo;
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
public class AdvanceAmountService {

    private final AdvanceAmountRepo advanceAmountRepo;
    private final EmployeeService employeeService;
    private final ParseUtil parseUtil;

    @Autowired
    public AdvanceAmountService(AdvanceAmountRepo advanceAmountRepo, EmployeeService employeeService, ParseUtil parseUtil) {
        this.advanceAmountRepo = advanceAmountRepo;
        this.employeeService = employeeService;
        this.parseUtil = parseUtil;
    }

    public ResponseEntity<List<AdvanceAmountResponse>> getAllAdvanceByEmployee(String orgId, String empId) throws EmployeeNotFoundException {

        List<AdvancePayment> advanceList = advanceAmountRepo.findAllByOrgIdAndEmpId(orgId,empId);
        List<AdvancePayment> data = advanceAmountRepo.findAll();
        String paidBy = employeeService.getEmployeeById(empId).getBody().getFullName();
        List<AdvanceAmountResponse> response = advanceList.stream()
                .map(advance -> AdvanceAmountResponse
                        .builder()
                        .id(advance.getId())
                        .createdDateTime(advance.getCreatedDateTime())
                        .advanceAmount(advance.getAdvanceAmount())
                        .paidById(advance.getPaidBy())
                        .paidBy(paidBy)
                        .build()).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<String> paidAdvance(AdvanceAmountRequest request) throws EmployeeNotFoundException {
        // TODO: 23/09/22  Validation is pending
        String paidBy = employeeService.getEmployeeById(request.getPaidBy()).getBody().getFullName();
        AdvancePayment advancePayment = parseUtil.prepareAdvanceAmountEntity(request,paidBy);
        advanceAmountRepo.save(advancePayment);
        return new ResponseEntity<>("Advance Amount is Paid",HttpStatus.CREATED);
        
    }

//    public ResponseEntity<String> paidBalance(String id, AdvancePaymentDto advancePaymentDto) {
//        // TODO: 28/09/22  check Employee & Advance
//        // TODO: 28/09/22 Prepare Advance model
//        // TODO: 28/09/22 Update Advance Data and status
//        return null;
//    }
}
