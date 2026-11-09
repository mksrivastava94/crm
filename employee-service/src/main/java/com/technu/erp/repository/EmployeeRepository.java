package com.technu.erp.repository;

import com.technu.erp.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee,String> {
    List<Employee> findAllByOrgId(String orgId);
    Optional<Employee> findFirstByAadharCardNoOrPanCardNo(String aadharCardNo, String panCardNo);
    Optional<Employee> findFirstByEmailIdOrPrimaryContactNo(String emailId,String contactNo);
    Optional<Employee> findFirstByOrgId(String orgId);
    Page<Employee> findAllByOrgId(String orgId, Pageable pageable);
}
