package com.technu.erp.service;

import com.amazonaws.util.StringUtils;
import com.technu.erp.external.UploadFeignClient;
import com.technu.erp.model.request.MaterialDto;
import com.technu.erp.model.request.MaterialUpdateRequest;
import com.technu.erp.exception.domain.MaterialExistException;
import com.technu.erp.exception.domain.MaterialImageNotFoundException;
import com.technu.erp.exception.domain.MaterialNotFoundException;
import com.technu.erp.model.entity.Material;
import com.technu.erp.model.request.FileUploadRequest;
import com.technu.erp.repository.MaterialRepository;
import com.technu.erp.util.BasicUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static com.technu.erp.constant.MaterialConstant.MATERIAL_NOT_FOUND;
import static com.technu.erp.constant.MaterialConstant.ORG_NAME;
import static com.technu.erp.enumeration.BucketName.MATERIAL;

@Service
@Log4j2
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final UploadFeignClient uploadFeignClient;

    @Autowired
    public MaterialService(MaterialRepository materialRepository, UploadFeignClient uploadFeignClient) {
        this.materialRepository = materialRepository;
        this.uploadFeignClient = uploadFeignClient;
    }

    public ResponseEntity<MaterialDto> createMaterial(MaterialDto materialDto) throws MaterialExistException, MaterialImageNotFoundException {
        log.info("Method createMaterial() started");
        isMaterialExist(materialDto.getMaterialName());
        String base64 = materialDto.getMaterialImageLink();
        materialDto.setMaterialImageLink("");
        Material material = BasicUtil.convertMaterialDtoToMaterial(materialDto);
        Material newMaterial = materialRepository.save(material);
        saveProductImageToS3(base64,newMaterial);
        newMaterial = materialRepository.save(newMaterial);

        log.info("Method createMaterial() ended");
        return new ResponseEntity<>(BasicUtil.convertMaterialToMaterialDto(newMaterial), HttpStatus.CREATED);
    }

    private Material saveProductImageToS3(String base64, Material newMaterial) throws MaterialImageNotFoundException {

        if (StringUtils.isNullOrEmpty(base64))
            throw new MaterialImageNotFoundException("Please select product Image");

        String path = ORG_NAME.concat("/").concat(newMaterial.getOrgId()).concat("/").concat(MATERIAL.getBucketName());

        FileUploadRequest uploadRequest = FileUploadRequest.builder()
                .id(newMaterial.getId())
                .path(path)
                .data(base64)
                .build();

        path = uploadFeignClient.uploadBase64(uploadRequest).getBody();
        newMaterial.setMaterialImageLink(path);
        return newMaterial;
    }

    public ResponseEntity<List<MaterialDto>> getAllMaterial(String orgId) {
        log.info("Method getAllMaterial() started");
        List<Material> materialList = materialRepository.findAllByOrgId(orgId);
        List<MaterialDto> materialDtoList = BasicUtil.convertMaterialListToMaterialDtoList(materialList);
        log.info("Method getAllMaterial() ended");
        return new ResponseEntity<>(materialDtoList, HttpStatus.OK);

    }

    public ResponseEntity<MaterialDto> getMaterialById(String id) throws MaterialNotFoundException {
        log.info("Method getMaterialById() started");
        Material eMaterial = findMaterialById(id);
        log.info("Method getMaterialById() ended");
        return new ResponseEntity<>(BasicUtil.convertMaterialToMaterialDto(eMaterial), HttpStatus.OK);
    }

    public Material findMaterialById(final String id) throws MaterialNotFoundException{
        return materialRepository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException(MATERIAL_NOT_FOUND));
    }

    public ResponseEntity<MaterialDto> updateMaterial(String id, MaterialDto materialDto) throws MaterialNotFoundException, ConstraintViolationException {
        log.info("Method updateMaterial() started");
        if(!id.equals(materialDto.getId()))
            throw new MaterialNotFoundException("Invalid Material Id");

        findMaterialById(id);
        Material materialUpdate = materialRepository.save(BasicUtil.convertMaterialDtoToMaterial(materialDto));
        log.info("Method updateMaterial() ended");
        return new ResponseEntity<>(BasicUtil.convertMaterialToMaterialDto(materialUpdate), HttpStatus.OK);
    }

    public void deleteMaterialById(String id) throws MaterialNotFoundException {
        log.info("Method deleteMaterialById() started");
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new MaterialNotFoundException(MATERIAL_NOT_FOUND));
        materialRepository.deleteById(material.getId());
        log.info("Method deleteMaterialById() started");
    }

    private void isMaterialExist(String materialName) throws MaterialExistException {
        Optional<Material> material = materialRepository.findByMaterialName(materialName);
        if (material.isPresent())
            throw new MaterialExistException("Material name already registered.");

    }

    public ResponseEntity<Integer> updateMaterialStock(String id, MaterialUpdateRequest updateRequest) throws MaterialNotFoundException {
        log.info("Method updateMaterialStock() started");
        Material eMaterial = findMaterialById(id);
        eMaterial.setQuantity(updateRequest.getQuantity());
        materialRepository.save(eMaterial);
        log.info("Method updateMaterialStock() ended");
        return ResponseEntity.ok(updateRequest.getQuantity());
    }
}
