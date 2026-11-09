package com.crm.erp.controller;

import java.util.List;

import com.crm.erp.model.response.KeyValueResponse;
import com.crm.erp.model.request.VendorDateRequest;
import com.crm.erp.model.request.VendorRequest;
import com.crm.erp.model.response.VendorPageResponse;
import com.crm.erp.model.response.VendorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.crm.erp.exception.ExceptionHandling;
import com.crm.erp.exception.domain.VendorExistException;
import com.crm.erp.exception.domain.VendorNotFoundException;
import com.crm.erp.service.VendorService;

@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController extends ExceptionHandling {

    private final VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/add")
    public ResponseEntity<VendorResponse> createVendor(@RequestBody VendorRequest request) throws VendorExistException {
        return vendorService.createVendor(request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VendorResponse> updateVendor(@PathVariable String id,@RequestBody VendorRequest request) throws VendorNotFoundException {
        return vendorService.updateVendor(id,request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteVendorById(@PathVariable String id) throws VendorNotFoundException {
        vendorService.deleteVendorById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorResponse> getVendorById(@PathVariable String id) throws VendorNotFoundException {
        return vendorService.getVendorById(id);
    }

    @GetMapping("/list/{orgId}/{page}/{pageSize}")
    public ResponseEntity<VendorPageResponse> getAllVendor(@PathVariable String orgId, @PathVariable int page, @PathVariable int pageSize){
        return vendorService.getAllVendor(orgId,page,pageSize);
    }

    @GetMapping("/list/key-value/{orgId}")
    public ResponseEntity<KeyValueResponse> getAllVendorKeyValue(@PathVariable String orgId){
        return vendorService.getAllVendorKeyValue(orgId);
    }

    @GetMapping("date/{orgId}")
    public ResponseEntity<List<VendorResponse>> getAllVendorByMonth(@PathVariable String orgId, @RequestBody VendorDateRequest vendorDateDto){
        return vendorService.getAllVendorByMonth(orgId,vendorDateDto);
    }

}
