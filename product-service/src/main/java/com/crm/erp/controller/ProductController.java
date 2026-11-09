package com.crm.erp.controller;

import com.crm.erp.model.request.ProductRequest;
import com.crm.erp.model.response.ProductPageResponse;
import com.crm.erp.exception.ExceptionHandling;
import com.crm.erp.exception.domain.ProductExistException;
import com.crm.erp.exception.domain.ProductImageNotFoundException;
import com.crm.erp.exception.domain.ProductNotFoundException;
import com.crm.erp.model.response.ProductResponse;
import com.crm.erp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController extends ExceptionHandling {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) throws ProductExistException, ProductImageNotFoundException {
        return productService.createProduct(request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id, @RequestBody ProductRequest request) throws ProductNotFoundException {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable String id) throws ProductNotFoundException {
        productService.deleteProductById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) throws ProductNotFoundException {
        return productService.getProductById(id);
    }

    @GetMapping("/list/{orgId}/{category}/{page}/{pageSize}")
    public ResponseEntity<ProductPageResponse> getAllProductByCategory(@PathVariable String orgId, @PathVariable String category, @PathVariable int page, @PathVariable int pageSize){
        return productService.getAllProduct(orgId,category,page,pageSize);
    }

    @GetMapping("/list/{orgId}/{page}/{pageSize}")
    public ResponseEntity<ProductPageResponse> getAllProduct(@PathVariable String orgId, @PathVariable int page, @PathVariable int pageSize){
        return productService.getAllProduct(orgId,null,page,pageSize);
    }

}
