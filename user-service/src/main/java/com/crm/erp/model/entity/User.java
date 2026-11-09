package com.crm.erp.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.crm.erp.model.response.UserResponse;
import com.crm.erp.util.BasicUtil;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import static com.crm.erp.util.Role.ROLE_MANAGER;

@Document(collection="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String emailId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String primaryContactNo;
    private String orgId;
    private LocalDate lastLoginDate;
    private LocalDate lastLoginDateDisplay;
    private String role = ROLE_MANAGER.name();
    private String[] authorities = ROLE_MANAGER.getAuthorities();
    private boolean isActive = true;
    private boolean isNotLocked = true;

    private LocalDate updatedDateTime = LocalDate.now();
    private LocalDate createdDateTime = LocalDate.now();

    public static User prepareDataForUserRegistration(UserResponse userDto) {

        User user = new User();
        user.firstName =userDto.getFirstName();
        user.lastName =userDto.getLastName();
        user.emailId =userDto.getEmailId();
        user.password =userDto.getPassword();
        user.primaryContactNo =userDto.getPrimaryContactNo();
        user.orgId = userDto.getOrgId();
        if(BasicUtil.notEmptyNotNull(userDto.getRole())){
            user.role = userDto.getRole();
            user.authorities=userDto.getAuthorities();
        }

        return user;
    }
}
