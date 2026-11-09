package com.technu.erp.repository;


import com.technu.erp.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends MongoRepository<Profile,String> {

    Optional<Profile> findFirstByEmailIdOrPrimaryContactNo(String emailId,String contactNo);

    long countByEmailIdOrPrimaryContactNo(String emailId, String primaryContactNo);
}
