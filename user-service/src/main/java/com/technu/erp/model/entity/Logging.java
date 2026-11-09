package com.technu.erp.model.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.ToString;

@Document(collection = "logging")
@Data
@ToString
public class Logging {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String emailId;
	private String primaryContactNo;
	private String orgId;
	private LocalDate createdDateTime = LocalDate.now();
	
	public static Logging prepareDataForUserLogging(User user) {

		Logging logging = new Logging();
		logging.firstName =user.getFirstName();
		logging.lastName =user.getLastName();
		logging.emailId =user.getEmailId();
		logging.primaryContactNo =user.getPrimaryContactNo();
		logging.orgId = user.getOrgId();

        return logging;
    }
}
