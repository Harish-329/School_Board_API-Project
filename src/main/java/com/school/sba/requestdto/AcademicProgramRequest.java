package com.school.sba.requestdto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcademicProgramRequest {

	private String programType;
	private String programName;
	private LocalDate programBeginsAt;
	private LocalDate programEndsAt;
	
}
