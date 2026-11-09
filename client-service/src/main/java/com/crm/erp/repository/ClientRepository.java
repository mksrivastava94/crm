package com.crm.erp.repository;

import com.crm.erp.model.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends MongoRepository<Client,String> {

    Optional<Client> findFirstByEmailIdOrPrimaryContactNo(String clientName, String primaryContactNo);
    List<Client> findAllByOrgId(String orgId);
    Optional<Client> findByEmailId(String emailId);
    Optional<Client> findByPrimaryContactNo(String primaryContactNo);
    Page<Client> findAllByOrgId(String orgId, Pageable pageable);
}
