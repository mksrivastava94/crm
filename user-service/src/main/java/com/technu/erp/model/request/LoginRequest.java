package com.technu.erp.model.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {

    private String emailId;
    private String password;
}
