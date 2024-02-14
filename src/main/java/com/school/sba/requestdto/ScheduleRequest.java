package com.school.sba.requestdto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequest {
	
	@NotNull(message = "schedule opening time should not be null")
	private LocalTime opensAt;
	
	@NotNull(message = "schedule closing time should not be null")
	private LocalTime closesAt;
	
	private int classHoursPerDay;
	
	private int classHoursLengthInMinutes;
	
	@NotNull(message = "break time should not be null")
	private LocalTime breakTime;
	
	private int breakLengthInMinutes;
	
	@NotNull(message = "lunch time should not be null")
	private LocalTime lunchTime;
	
	private int lunchLengthInMinutes;
}
