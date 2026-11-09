package com.technu.erp.service;


import com.technu.erp.model.request.ProductUpdateRequest;
import com.technu.erp.exception.domain.ProductNotFoundException;
import com.technu.erp.model.entity.Product;
import com.technu.erp.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class ProductStockService {

    private ProductRepository productRepository;
    private ProductService productService;

    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<Integer> updateStock(String id, ProductUpdateRequest request) throws ProductNotFoundException {
        log.info("Method updateStock() started");
        Product eProduct = productService.findProductById(id);
        eProduct.setQuantity(request.getQuantity());
        productRepository.save(eProduct);
        log.info("Method updateStock() end");
        return ResponseEntity.ok(request.getQuantity());

    }
}
