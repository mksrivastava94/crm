package com.technu.erp.repository;

import com.technu.erp.model.entity.Production;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRepository extends MongoRepository<Production,String> {
}
