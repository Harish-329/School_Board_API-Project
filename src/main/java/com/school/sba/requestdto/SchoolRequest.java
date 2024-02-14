package com.school.sba.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolRequest {
	
	@NotBlank(message = "school name should not be blank")
	@NotNull(message = "school name should not be null")
	private String schoolName;
	
	@NotNull(message = "school contact number should not be null")
	@NotBlank(message = "school contact number should not be blank")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "phone number should be exactly 10 digits")
	private String schoolContactNumber;
	
	@NotNull(message = "emailId should not be null")
	@NotBlank(message = "emailId should not be empty")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email")
	private String schoolEmailId;
	
	@NotNull(message = "address should not be null")
	@NotBlank(message = "address should not be empty")
	private String schoolAddress;

}
