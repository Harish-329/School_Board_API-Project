package com.school.sba.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolRequest {
	
	private String schoolName;
	private Long schoolContactNumber;
	private String schoolEmailId;
	private String schoolAddress;
	private String weekOffDay;

}
