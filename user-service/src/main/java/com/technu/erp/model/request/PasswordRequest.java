package com.technu.erp.model.request;

import lombok.Data;

@Data
public class PasswordRequest {

    private String oldPassword;
    private String newPassword;
}