package com.technu.erp.repository;

import com.technu.erp.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {
    Optional<Product> findByProductName(String productName);
    Page<Product> findAllByOrgId(String orgId, Pageable pageable);
    Page<Product> findAllByOrgIdAndCategory(String orgId, String category, Pageable pageable);
}
