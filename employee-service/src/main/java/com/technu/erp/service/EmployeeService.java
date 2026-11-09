package com.technu.erp.service;

import com.technu.erp.exception.domain.EmployeeExistException;
import com.technu.erp.exception.domain.EmployeeNotFoundException;
import com.technu.erp.model.entity.Employee;
import com.technu.erp.model.request.DateTimeDto;
import com.technu.erp.model.request.employee.EmpUpdateRequest;
import com.technu.erp.model.request.employee.EmployeeRequest;
import com.technu.erp.model.request.KeyValueDto;
import com.technu.erp.model.response.employee.EmployeePageResponse;
import com.technu.erp.model.response.employee.EmployeeResponse;
import com.technu.erp.repository.EmployeeRepository;
import com.technu.erp.util.ParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.technu.erp.constant.EmployeeConstant.ALREADY_REGISTER_AADHAR_OR_PANCARD;
import static com.technu.erp.constant.EmployeeConstant.EMPLOYEE_NOT_FOUND;

@Service
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ParseUtil parseUtil;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, ParseUtil parseUtil) {
        this.employeeRepository = employeeRepository;
        this.parseUtil = parseUtil;
    }

    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<EmployeeResponse> createEmployee(EmployeeRequest request) throws EmployeeExistException {
        log.info("Method  createEmployee() started");
        isEmailOrPhoneExist(request.getEmailId(),request.getPrimaryContactNo());
        Employee employee = parseUtil.prepareEmployeeEntity(request);
        isAadharOrPancardExist(employee.getAadharCardNo(),employee.getPanCardNo());
        Employee newEmployee = employeeRepository.save(employee);
        EmployeeResponse newEmployeeDto = parseUtil.modelToResponse(newEmployee);
        log.info("Method  createEmployee() ended");
        return new ResponseEntity<>(newEmployeeDto, HttpStatus.CREATED);

    }

    public ResponseEntity<EmployeePageResponse> getAllEmployee(final String orgId, final int page, final int pageSize) {
        log.info("Method  getAllEmployee() started");
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Employee> pages =employeeRepository.findAllByOrgId(orgId,pageable);
        EmployeePageResponse employeePageDto = getEmployeePageData(pages);
        log.info("Method  getAllEmployee() ended");
        return new ResponseEntity<>(employeePageDto,HttpStatus.OK);
    }

    private EmployeePageResponse getEmployeePageData(Page<Employee> pages) {
        EmployeePageResponse response = new EmployeePageResponse();
        response.setEmployeeList(parseUtil.empListToEmpResponseList(pages.getContent()));
        response.setTotalPage(pages.getTotalPages());
        return  response;
    }

    public ResponseEntity<EmployeeResponse> getEmployeeById(String id) throws EmployeeNotFoundException {
        log.info("Method  getEmployeeById() started");
        Employee eEmployee = isEmployeeExistById(id);
        EmployeeResponse response = parseUtil.modelToResponse(eEmployee);
        log.info("Method  getEmployeeById() ended");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<EmployeeResponse> updateEmployee(String id, EmpUpdateRequest request) throws EmployeeNotFoundException, ConstraintViolationException {
        log.info("Method  updateEmployee() started");

        Employee emp = isEmployeeExistById(id);
        if(emp == null){
            throw new EmployeeNotFoundException("Invalid Employee Id : "+id);
        }

        Employee employee = parseUtil.updateRequestToEntity(request);
        Employee empUpdate = employeeRepository.save(employee);
        EmployeeResponse response = parseUtil.modelToResponse(empUpdate);
        log.info("Method  updateEmployee() ended");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void deleteEmployeeById(String id) throws EmployeeNotFoundException {
        log.info("Method  deleteEmployeeById() started");
        isEmployeeExistById(id);
        employeeRepository.deleteById(id);
        log.info("Method  deleteEmployeeById() ended");
    }

    private void isAadharOrPancardExist(String aadharCardNo, String panCardNo) throws EmployeeExistException {

        Optional<Employee> eEmployee = employeeRepository.findFirstByAadharCardNoOrPanCardNo(aadharCardNo,panCardNo);
        if(eEmployee.isPresent()) {
            log.error(ALREADY_REGISTER_AADHAR_OR_PANCARD);
            throw new EmployeeExistException(ALREADY_REGISTER_AADHAR_OR_PANCARD);
        }
    }


    private void isEmailOrPhoneExist(String emailId, String primaryContactNo) throws EmployeeExistException {
        Optional<Employee> employee = employeeRepository.findFirstByEmailIdOrPrimaryContactNo(
                emailId,primaryContactNo);

        if(employee.isPresent())
            throw new EmployeeExistException("Phone number or email Id is already register");

    }

    private Employee isEmployeeExistById(String id) throws  EmployeeNotFoundException {
        return employeeRepository.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND));
    }

    public ResponseEntity<List<EmployeeResponse>> getAllEmployeeByMonth(String orgId, DateTimeDto clientDateDto) {
        log.info("Method getAllEmployeeByMonth() is started");
        List<Employee> empList = employeeRepository.findAllByOrgId(orgId).stream()
                .filter(employee -> employee.getCreatedDateTime().getMonthValue() == clientDateDto.getMonth()
                        && employee.getCreatedDateTime().getYear() == clientDateDto.getYear())
                .collect(Collectors.toList());
        log.info("Method getAllEmployeeByMonth() is ended");

        return new ResponseEntity<>(parseUtil.empListToEmpResponseList(empList),HttpStatus.OK);
    }

    public ResponseEntity<List<KeyValueDto>> getAllEmployeeKeyValue(String orgId) {
        log.info("Method getAllEmployee() is started");
        List<KeyValueDto> employeeKeyValueList = employeeRepository.findAllByOrgId(orgId)
                .stream()
                .map(emp->new KeyValueDto(emp.getFullName(),emp.getId()))
                .collect(Collectors.toList());

        log.info("Method getAllEmployee() is ended");

        return new ResponseEntity<>(employeeKeyValueList,HttpStatus.OK);

    }
}
