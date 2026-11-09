package com.crm.erp.util;

import com.crm.erp.model.entity.Logging;
import com.crm.erp.model.entity.User;
import com.crm.erp.model.request.NewUserRequest;
import com.crm.erp.model.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ParseUtil {


    public static User prepareNewUserData(NewUserRequest request, PasswordEncoder bCryptPasswordEncoder){
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .emailId(request.getEmailId())
                .primaryContactNo(request.getPrimaryContactNo())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_MANAGER.name())
                .authorities(Role.ROLE_MANAGER.getAuthorities())
                .createdDateTime(LocalDate.now())
                .updatedDateTime(LocalDate.now())
                .isActive(true)
                .isNotLocked(true)
                .orgId(request.getOrgId())
                .build();
    }


    public static User convertUserDtoToUser(UserResponse userResponse){
        return new ModelMapper().map(userResponse,User.class);
    }

    public static List<UserResponse> convertUserListToUserDtoList(List<User> userList) {
        return  userList.stream()
                .map(user->new ModelMapper().map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    public static Logging convertUserToLogging(User loginUser) {
        return new ModelMapper().map(loginUser,Logging.class);
    }

    public static UserResponse convertUserToUserDto(User user){
        return new ModelMapper().map(user, UserResponse.class);
    }
}
