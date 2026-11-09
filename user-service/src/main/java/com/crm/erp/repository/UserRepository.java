package com.crm.erp.repository;

import com.crm.erp.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    List<User> findAllByOrgId(String orgId);

    Optional<User> findByEmailId(String emailId);
    long countByEmailId(String emailId);

//    Optional<User> findByEmailIdIgnoreCase(String emailId);
//    long countByEmailIdIgnoreCase(String emailId);

}
