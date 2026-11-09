package com.technu.erp.model.request;

import lombok.Data;

@Data
public class FileUploadRequest {
    String id;
    String path;
    String data;
}
