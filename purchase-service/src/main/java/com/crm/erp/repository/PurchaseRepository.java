package com.crm.erp.repository;

import com.crm.erp.model.Purchase;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends MongoRepository<Purchase,String> {

	List<Purchase> findAllByOrgId(String orgId);
}
