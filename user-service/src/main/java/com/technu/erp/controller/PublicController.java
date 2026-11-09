package com.technu.erp.controller;

import com.technu.erp.model.request.LoginRequest;
import com.technu.erp.model.response.UserResponse;
import com.technu.erp.exception.ExceptionHandling;
import com.technu.erp.exception.domain.EmailNotFoundException;
import com.technu.erp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("/api/v1/public/user")
public class PublicController extends ExceptionHandling {

    private final UserService userService;

    @Autowired
    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest) throws ConstraintViolationException {
        return userService.login(loginRequest);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody LoginRequest loginRequest) throws EmailNotFoundException {
        return userService.forgetPassword(loginRequest);
    }
}
