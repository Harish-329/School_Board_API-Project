package com.school.sba.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
	
	@NotNull(message = "user name should not be null")
	@NotBlank(message = "user name should not be empty")
	private String userName;
	
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain at least "
			+ "one uppercase letter, "
			+ "one lowercase letter, "
			+ "one number, "
			+ "one special character")
	private String userPassword;
	
	@NotNull(message = "first name should not be null")
	@NotBlank(message = "first name should not be empty")
	private String userFirstName;
	
	@NotNull(message = "last name should not be null")
	@NotBlank(message = "last name should not be empty")
	private String userLastName;
	
	@NotNull(message = "contact number should not be null")
	@NotBlank(message = "contact number should not be empty")
	@Pattern(regexp="(^$|[0-9]{10})", message = "contact should contain 10 numbers")
	private String userContact;
	
	@NotNull(message = "emailId should not be null")
	@NotBlank(message = "emailId should not be empty")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email")
	private String userEmail;
	
	@NotBlank(message = "user role should not be blank")
	@NotNull(message = "user role should not be null")
	private String userRole;

}
