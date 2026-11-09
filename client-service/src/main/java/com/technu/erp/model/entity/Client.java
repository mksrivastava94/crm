package com.technu.erp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

import static com.technu.erp.constant.ClientConstant.*;

@Document(collection="client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    private String id;

    @NotEmpty(message = CLIENT_NAME_NOT_EMPTY)
    private String clientName;

    @Length(min = 10,max = 10,message = ENTER_VALID_PHONE_NO)
    private String primaryContactNo;
    private String secondaryContactNo;

    @NotEmpty(message = ENTER_VALID_EMAIL)
    @Email(message = ENTER_VALID_EMAIL)
    private String emailId;

    private String address;
    private String pinCode;
    private String country;
    private String state;
    private String orgId;

    private LocalDate createdDate;
    private int month;
    private int year;

}
