package com.crm.erp.controller;

import com.crm.erp.model.request.NewUserRequest;
import com.crm.erp.model.request.PasswordRequest;
import com.crm.erp.model.response.UserResponse;
import com.crm.erp.exception.ExceptionHandling;
import com.crm.erp.exception.domain.*;
import com.crm.erp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends ExceptionHandling {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> registerUser(@RequestBody NewUserRequest newUserRequest) throws ConstraintViolationException, EmailExistException {
        return userService.registerUser(newUserRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @GetMapping("/list/{orgId}")
    public ResponseEntity<List<UserResponse>> getAllUser(@PathVariable String orgId){
        return userService.getAllUser(orgId);
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<UserResponse> changePassword(@PathVariable String id, @RequestBody PasswordRequest passwordRequest) throws PasswordUnMatchException, UserNotFoundException, InvalidInputException {
        return userService.changePassword(id,passwordRequest);
    }

}
