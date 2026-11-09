package com.technu.erp.external;

import com.technu.erp.exception.domain.ProductNotFoundException;
import com.technu.erp.model.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product")
public interface ProductFeignClient {

    @GetMapping("/api/v1/product/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) throws ProductNotFoundException;
}
