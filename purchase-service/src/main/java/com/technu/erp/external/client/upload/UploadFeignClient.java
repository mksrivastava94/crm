package com.technu.erp.external.client.upload;

import com.technu.erp.model.request.FileUploadRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("upload")
public interface UploadFeignClient {


    @PostMapping(value="/api/v1/upload/base64",consumes = "application/json")
    ResponseEntity<String> uploadBase64(@RequestBody FileUploadRequest request);
}