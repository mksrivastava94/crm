package com.crm.erp.controller;

import com.crm.erp.model.request.MaterialDto;
import com.crm.erp.model.request.MaterialUpdateRequest;
import com.crm.erp.exception.ExceptionHandling;
import com.crm.erp.exception.domain.MaterialExistException;
import com.crm.erp.exception.domain.MaterialImageNotFoundException;
import com.crm.erp.exception.domain.MaterialNotFoundException;
import com.crm.erp.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/material")
public class MaterialController extends ExceptionHandling {


    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping("/add")
    public ResponseEntity<MaterialDto> createMaterial(@RequestBody MaterialDto materialDto) throws MaterialExistException, MaterialImageNotFoundException {
        return materialService.createMaterial(materialDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MaterialDto> updateMaterial(@PathVariable String id, @RequestBody MaterialDto materialDto) throws MaterialNotFoundException {
        return materialService.updateMaterial(id,materialDto);
    }

    @PutMapping("/update/stock/{id}")
    public ResponseEntity<Integer> updateMaterialStock(@PathVariable String id, @RequestBody MaterialUpdateRequest updateRequest) throws MaterialNotFoundException {
        return materialService.updateMaterialStock(id,updateRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMaterialById(@PathVariable String id) throws MaterialNotFoundException {
        materialService.deleteMaterialById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialDto> getMaterialById(@PathVariable String id) throws MaterialNotFoundException {
        return materialService.getMaterialById(id);
    }

    @GetMapping("/list/{orgId}")
    public ResponseEntity<List<MaterialDto>> getAllMaterial(@PathVariable String orgId){
        return materialService.getAllMaterial(orgId);
    }

}
