package com.crm.erp.repository;

import com.crm.erp.model.entity.Production;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRepository extends MongoRepository<Production,String> {
}
