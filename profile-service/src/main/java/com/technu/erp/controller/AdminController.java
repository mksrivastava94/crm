package com.technu.erp.controller;

import com.technu.erp.dto.ProfileDateDto;
import com.technu.erp.dto.ProfileDto;
import com.technu.erp.exception.ExceptionHandling;
import com.technu.erp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/profile")
public class AdminController extends ExceptionHandling {

    @Autowired
    private AdminService adminService;

    @GetMapping("/list")
    public ResponseEntity<List<ProfileDto>> getAllProfile(){
        return adminService.getAllProfile();
    }

    @GetMapping("/month")
    public ResponseEntity<List<ProfileDto>> getAllProfileByMonth(@RequestBody ProfileDateDto profileDateDto){
        return adminService.getAllProfileByMonth(profileDateDto);
    }
}
