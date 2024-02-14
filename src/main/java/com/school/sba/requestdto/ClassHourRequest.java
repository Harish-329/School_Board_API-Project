package com.school.sba.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassHourRequest {
	
	private int classHourId;

	private int classRoomNumber;

	private int teacherId;
	
	private int subjectId;

}
