package com.school.sba.requestdto;

import com.school.sba.entity.School;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
	
	@NotNull(message = "user name should not be null")
	@NotBlank(message = "user name should not be empty")
	private String userName;
	
//	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
//	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain at least "
//			+ "one uppercase letter"
//			+ "one lowercase letter"
//			+ "one number"
//			+ "one special character")
	private String userPassword;
	
	@NotEmpty(message = "first name should not be empty")
	private String userFirstName;
	
	@NotEmpty(message = "last name should not be empty")
	private String userLastName;
	
//	@NotEmpty(message = "phone number should not be empty")
	private Long userContact;
	
	@NotEmpty(message = "email should not be empty")
//	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email")
	private String userEmail;
	
	private School school;
	
	private String userRole;

}
