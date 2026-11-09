package com.technu.erp.service;

import com.technu.erp.dto.ProfileDateDto;
import com.technu.erp.dto.ProfileDto;
import com.technu.erp.model.Profile;
import com.technu.erp.repository.ProfileRepository;
import com.technu.erp.util.BasicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private ProfileRepository profileRepository;


    public ResponseEntity<List<ProfileDto>> getAllProfile() {
        LOGGER.info("Method is getAllProfile()");
        List<Profile> profileList = profileRepository.findAll();
        List<ProfileDto> profileDtoList = BasicUtil.convertProfileListToProfileDtoList(profileList);
        return new ResponseEntity<>(profileDtoList, HttpStatus.OK);
    }

    public ResponseEntity<List<ProfileDto>> getAllProfileByMonth(ProfileDateDto profileDateDto) {
        LOGGER.info("Method getAllProfileByMonth() is started");
        List<Profile> profileList = profileRepository.findAll().stream()
                .filter(profile -> profile.getCreatedDateTime().getMonthValue() == profileDateDto.getMonth()
                        && profile.getCreatedDateTime().getYear() == profileDateDto.getYear())
                .collect(Collectors.toList());

        LOGGER.info("Method getAllProfileByMonth() is ended");
        return new ResponseEntity<>(BasicUtil.convertProfileListToProfileDtoList(profileList),HttpStatus.OK);
    }
}
