package com.crm.erp.repository;

import com.crm.erp.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
    List<Order> findAllByOrgId(String orgId);
    Page<Order> findAllByOrgId(String orgId, Pageable pageable);

    List<Order> findAllByOrgIdAndOrderBy(String orgId, String orderBy);
}