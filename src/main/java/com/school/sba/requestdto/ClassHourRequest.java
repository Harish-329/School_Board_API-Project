package com.school.sba.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassHourRequest {
	
//	@NotNull(message = "class hour Id should not be null")
//	@NotBlank(message = "class hour Id should not be blank")
	private int classHourId;
	
//	@NotNull(message = "class room number should not be null")
//	@NotBlank(message = "class room number should not be blank")
	private int classRoomNumber;
	
//	@NotNull(message = "user/teacher id should not be null")
//	@NotBlank(message = "user/teacher Id should not be blank")
	private int teacherId;
	
//	@NotNull(message = "subject Id should not be null")
//	@NotBlank(message = "subject hour Id should not be blank")
	private int subjectId;

}
