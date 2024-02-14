package com.school.sba.requestdto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelRequest {
	
	@NotNull(message = "from date should not be null")
	LocalDate fromDate;
	@NotNull(message = "to date should not be null")
	LocalDate toDate;
	@NotNull(message = "file path should not be null")
	@NotBlank(message = "file path should not be empty")
	String filePath;

}
