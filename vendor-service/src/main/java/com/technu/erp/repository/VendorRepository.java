package com.technu.erp.repository;

import com.technu.erp.model.entity.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorRepository extends MongoRepository<Vendor,String> {
    List<Vendor> findAllByOrgId(String orgId);
    Optional<Vendor> findFirstByEmailIdOrPrimaryContactNo(String emailId, String contactNo);
    Page<Vendor> findAllByOrgId(String orgId, Pageable pageable);
}
