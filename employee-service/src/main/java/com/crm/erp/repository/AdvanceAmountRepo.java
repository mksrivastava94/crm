package com.crm.erp.repository;

import com.crm.erp.model.entity.AdvancePayment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvanceAmountRepo extends MongoRepository<AdvancePayment,String> {
    List<AdvancePayment> findAllByEmpId(String empId);
    List<AdvancePayment> findAllByOrgIdAndEmpId(String empId, String orgId);
}
