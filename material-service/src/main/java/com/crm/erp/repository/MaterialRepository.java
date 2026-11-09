package com.crm.erp.repository;

import com.crm.erp.model.entity.Material;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRepository extends MongoRepository<Material,String> {

    Optional<Material> findByMaterialName(String materialName);

    List<Material> findAllByOrgId(String orgId);
}
