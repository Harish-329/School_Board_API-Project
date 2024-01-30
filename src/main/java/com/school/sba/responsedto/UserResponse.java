package com.school.sba.responsedto;

import java.util.List;

import com.school.sba.entity.Subject;
import com.school.sba.enums.UserRole;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
	
	private Integer userId;
	private String userName;
	private String userFirstName;
	private String userLastName;
	private Long userContact;
	private String userEmail;
	private UserRole userRole;
	
	private List<String> listOfAcademicPrograms;
	
	private Subject subject;

}
