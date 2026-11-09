package com.technu.erp.controller;

import com.technu.erp.exception.ExceptionHandling;
import com.technu.erp.exception.domain.ProductExistException;
import com.technu.erp.exception.domain.ProductImageNotFoundException;
import com.technu.erp.model.request.FileUploadRequest;
import com.technu.erp.service.FileUploadService;
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
