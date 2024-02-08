package com.school.sba.requestdto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExcelRequest {
	
	LocalDate fromDate;
	LocalDate toDate;
	String filePath;
//	MultipartFile file;

}
