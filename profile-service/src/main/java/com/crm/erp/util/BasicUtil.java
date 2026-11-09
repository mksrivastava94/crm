package com.crm.erp.util;

import com.crm.erp.dto.ProfileCreateDto;
import com.crm.erp.dto.ProfileDto;
import com.crm.erp.dto.UserDto;
import com.crm.erp.model.Profile;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BasicUtil {

	private BasicUtil() {
	}

	public static boolean notEmptyNotNull(String input) {
		return !(input == null || input.isEmpty());
	}

	public static boolean notEmptyNotNull(Integer input) {
		return input != null && input > 0;
	}

	public static boolean isValidatePhoneNumber(String input) {
		return input.length() == 10;
	}

	public static boolean isValidEmailId(String emailId) {
		String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

		Pattern pat = Pattern.compile(emailRegex);
		if (emailId == null)
			return false;
		return pat.matcher(emailId).matches();
	}

	public static ProfileDto convertProfileToProfileDto(Profile profile) {
		return new ModelMapper().map(profile,ProfileDto.class);
	}

	public static Profile convertProfileDtoToProfile(ProfileDto profileDto) {
		return new ModelMapper().map(profileDto,Profile.class);
	}

	public static Profile convertProfileCreateDtoToProfile(ProfileCreateDto profileDto) {
		return new ModelMapper().map(profileDto,Profile.class);
	}

	public static UserDto convertProfileCreateDtoUserDto(ProfileCreateDto profileCreateDto) {
		return new ModelMapper().map(profileCreateDto,UserDto.class);
	}

	public static List<ProfileDto> convertProfileListToProfileDtoList(List<Profile> profileList) {
		return profileList.stream().map(post -> new ModelMapper().map(post, ProfileDto.class))
				.collect(Collectors.toList());
	}
}
