package com.crm.erp.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUploadRequest {
    String id;
    String path;
    String data;
}
