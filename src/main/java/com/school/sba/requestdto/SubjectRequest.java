package com.school.sba.requestdto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequest {
	
	@NotEmpty(message = "list of subject names should contain data, it should not be empty")
	private List<String> subjectNames;
	
}
