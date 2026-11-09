package com.technu.erp.controller;

import com.technu.erp.dto.ProfileCreateDto;
import com.technu.erp.dto.ProfileDateDto;
import com.technu.erp.dto.ProfileDto;
import com.technu.erp.dto.ProfileUpdateDto;
import com.technu.erp.exception.ExceptionHandling;
import com.technu.erp.exception.domain.ProfileExistException;
import com.technu.erp.exception.domain.ProfileNotFoundException;
import com.technu.erp.exception.domain.UserServiceDownException;
import com.technu.erp.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController extends ExceptionHandling {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProfileDto> createProfile(@RequestBody ProfileCreateDto profileCreateDto) throws ProfileExistException, UserServiceDownException {
        return profileService.createProfile(profileCreateDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteProfileById(@PathVariable String id) throws ProfileNotFoundException {
            return profileService.deleteProfileById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable String id, @RequestBody ProfileUpdateDto profileUpdateDto) throws ProfileNotFoundException {
        return profileService.updateProfile(id, profileUpdateDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getProfileById(@PathVariable String id) throws  ProfileNotFoundException {
        return profileService.getProfileById(id);
    }

}
