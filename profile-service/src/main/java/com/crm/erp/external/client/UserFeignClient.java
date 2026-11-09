package com.crm.erp.external.client;

import com.crm.erp.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.ConstraintViolationException;

@FeignClient(name = "user")
public interface UserFeignClient {

    @PostMapping(value="/api/v1/user/create",consumes = "application/json")
    ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) throws ConstraintViolationException;
}


