package com.technu.erp.repository;

import com.technu.erp.model.Purchase;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends MongoRepository<Purchase,String> {

	List<Purchase> findAllByOrgId(String orgId);
}
