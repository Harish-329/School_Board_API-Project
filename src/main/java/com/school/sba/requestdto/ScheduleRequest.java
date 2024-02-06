package com.school.sba.requestdto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequest {
	
	@NotNull(message = "schedule opening time should not be null")
	@NotBlank(message = "schedule opening time should not be blank")
	private LocalTime opensAt;
	
	@NotNull(message = "schedule closing time should not be null")
	@NotBlank(message = "schedule closing time should not be blank")
	private LocalTime closesAt;
	
	@NotBlank(message = "number of classes per day should not be blank")
	private int classHoursPerDay;
	
	@NotBlank(message = "length of class hour should not be blank and it should be in minutes")
	private int classHoursLengthInMinutes;
	
	@NotNull(message = "break time should not be null")
	@NotBlank(message = "break time should not be blank")
	private LocalTime breakTime;
	
	@NotBlank(message = "length of length should not be blank and it should be in minutes")
	private int breakLengthInMinutes;
	
	@NotNull(message = "lunch time should not be null")
	@NotBlank(message = "lunch time should not be blank")
	private LocalTime lunchTime;
	
	@NotBlank(message = "length of length should not be blank and it should be in minutes")
	private int lunchLengthInMinutes;
}
