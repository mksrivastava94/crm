package com.crm.erp.model.response.employee;

import com.crm.erp.model.response.employee.EmployeeResponse;
import lombok.*;

import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePageResponse {

    private List<EmployeeResponse> employeeList;
    private int totalPage;

}
