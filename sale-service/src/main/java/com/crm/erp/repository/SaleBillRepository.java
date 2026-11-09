package com.crm.erp.repository;

import com.crm.erp.model.SaleBill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleBillRepository extends MongoRepository<SaleBill,String> {
    List<SaleBill> findAllByOrgId(String orgId);
}
