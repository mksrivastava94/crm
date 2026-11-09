package com.technu.erp.model;

import com.technu.erp.constant.ProfileConstant;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Document(collection="profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Profile {

        @Id
        private String id;

        @NotEmpty(message = ProfileConstant.PROFILE_NAME_NOT_EMPTY)
        private String firstName;
        private String lastName;

        @NotEmpty(message = ProfileConstant.ORG_NAME_NOT_EMPTY)
        private String organizationName;

        @NotEmpty(message = ProfileConstant.ENTER_VALID_EMAIL)
        @Email(message = ProfileConstant.ENTER_VALID_EMAIL)
        private String emailId;

        @Length(min = 10,max = 10,message = ProfileConstant.ENTER_VALID_PHONE_NO)
        private String primaryContactNo;
        private String sector;
        private boolean isActive = true;

        private LocalDate createdDateTime = LocalDate.now();
        private LocalDate expiredDateTime = LocalDate.now().plusDays(15);
}
