package com.technu.erp.service;

import static com.technu.erp.constant.ProfileConstant.PROFILE_NOT_FOUND;
import static com.technu.erp.util.Role.ROLE_ADMIN;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.technu.erp.dto.*;
import com.technu.erp.external.client.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.technu.erp.exception.domain.ProfileExistException;
import com.technu.erp.exception.domain.ProfileNotFoundException;
import com.technu.erp.exception.domain.UserServiceDownException;
import com.technu.erp.model.Profile;
import com.technu.erp.repository.ProfileRepository;
import com.technu.erp.util.BasicUtil;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Service
public class ProfileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileService.class);

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private UserFeignClient userFeignClient;


	@Transactional(rollbackFor = Throwable.class)
	@CircuitBreaker(name = "userService", fallbackMethod = "createProfileFallback")
	public ResponseEntity<ProfileDto> createProfile(ProfileCreateDto profileCreateDto)
			throws ProfileExistException, ConstraintViolationException, UserServiceDownException {
		LOGGER.info("Method createProfile() is started");
		Profile profile = BasicUtil.convertProfileCreateDtoToProfile(profileCreateDto);
		LOGGER.info("Profile is created Id is {} ", profile.getId());
		isEmailOrPhoneExist(profile.getEmailId(), profile.getPrimaryContactNo());
		Profile newProfile = profileRepository.save(profile);
		profileCreateDto.setId(newProfile.getId());
		UserDto userDto = BasicUtil.convertProfileCreateDtoUserDto(profileCreateDto);
		userDto.setOrgId(newProfile.getId());
		userDto.setRole(ROLE_ADMIN.name());
		userDto.setAuthorities(ROLE_ADMIN.getAuthorities());
		userFeignClient.registerUser(userDto).getBody();
		ProfileDto profileDto = BasicUtil.convertProfileToProfileDto(newProfile);
		LOGGER.info("Method createProfile() is ended");
		return new ResponseEntity<>(profileDto, HttpStatus.CREATED);
	}

	public ResponseEntity<ProfileDto> createProfileFallback(ProfileCreateDto profileCreateDto, Exception e)
			throws ProfileExistException, ConstraintViolationException, UserServiceDownException {
		LOGGER.info("Method createProfileFallback() is started");
		LOGGER.error("Error in create profile : {} ",e.getMessage());
		if (!e.getMessage().isEmpty()) {
			if (e instanceof ProfileExistException) {
				throw new ProfileExistException("Phone number or email Id is already register");
			} else if (e instanceof ConstraintViolationException)
				throw new ConstraintViolationException((Set<? extends ConstraintViolation<?>>) e);
			if (!profileCreateDto.getId().isEmpty()) {
				profileRepository.deleteById(profileCreateDto.getId());
				throw new UserServiceDownException("User service down please contact admin");
			}
		}
		return null;
	}

	public ResponseEntity<ProfileDto> getProfileById(String id) throws ProfileNotFoundException {
		LOGGER.info("Method getProfileById() is started");
		Profile eProfile = findProfileById(id);
		LOGGER.info("Method getProfileById() is ended");
		return new ResponseEntity<>(BasicUtil.convertProfileToProfileDto(eProfile), HttpStatus.OK);
	}


	public ResponseEntity<HttpStatus> deleteProfileById(String id) throws ProfileNotFoundException {
		LOGGER.info("Method deleteProfileById() is started");
		Profile eProfile = findProfileById(id);
		eProfile.setActive(false);
		profileRepository.save(eProfile);
		LOGGER.info("Method deleteProfileById() is ended");
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Transactional(rollbackFor = Throwable.class)
	public ResponseEntity<ProfileDto> updateProfile(String id, ProfileUpdateDto profileUpdateDto)
			throws ProfileNotFoundException, ConstraintViolationException {

		LOGGER.info("Method updateProfile() is started");
		Profile eProfile = findProfileById(id);
		validateProfileUpdateRequest(profileUpdateDto, eProfile);
		eProfile = profileRepository.save(eProfile);
		LOGGER.info("Method updateProfile() is ended");
		return new ResponseEntity<>(BasicUtil.convertProfileToProfileDto(eProfile), HttpStatus.ACCEPTED);
	}

	private void isEmailOrPhoneExist(String emailId, String primaryContactNo) throws ProfileExistException {
		if( profileRepository.countByEmailIdOrPrimaryContactNo(emailId, primaryContactNo) > 0)
			throw new ProfileExistException("Phone number or email Id is already register");
	}

	private Profile validateProfileUpdateRequest(ProfileUpdateDto profileUpdateDto, Profile eProfile) {
		if (BasicUtil.notEmptyNotNull(profileUpdateDto.getEmailId()))
			eProfile.setEmailId(profileUpdateDto.getEmailId());
		if (BasicUtil.notEmptyNotNull(profileUpdateDto.getPrimaryContactNo()))
			eProfile.setPrimaryContactNo(profileUpdateDto.getPrimaryContactNo());
		if (BasicUtil.notEmptyNotNull(profileUpdateDto.getSector()))
			eProfile.setSector(profileUpdateDto.getSector());
		return eProfile;
	}


	private Profile findProfileById(String id) throws ProfileNotFoundException {
		return profileRepository.findById(id)
				.orElseThrow(() -> new ProfileNotFoundException(PROFILE_NOT_FOUND));
	}
}
