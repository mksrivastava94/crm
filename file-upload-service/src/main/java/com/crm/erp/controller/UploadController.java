package com.crm.erp.controller;

import com.crm.erp.exception.ExceptionHandling;
import com.crm.erp.exception.domain.ProductExistException;
import com.crm.erp.exception.domain.ProductImageNotFoundException;
import com.crm.erp.model.request.FileUploadRequest;
import com.crm.erp.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/upload")
public class UploadController extends ExceptionHandling {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/base64")
    public String uploadBase64(@RequestBody FileUploadRequest request) throws Exception {
        return fileUploadService.uploadBase64(request.getId(),request.getPath(),request.getData());
    }

}
