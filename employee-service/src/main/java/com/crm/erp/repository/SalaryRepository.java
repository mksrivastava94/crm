package com.crm.erp.repository;

import com.crm.erp.model.entity.Salary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaryRepository extends MongoRepository<Salary,String> {
    List<Salary> findAllByOrgIdAndEmpId(String orgId, String empId);
}