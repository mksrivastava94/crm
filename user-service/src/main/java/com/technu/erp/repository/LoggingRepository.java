package com.technu.erp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.technu.erp.model.entity.Logging;

@Repository
public interface LoggingRepository extends MongoRepository<Logging, String> {

}
