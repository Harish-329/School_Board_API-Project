package com.school.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.requestdto.ScheduleRequest;
import com.school.sba.responsedto.ScheduleResponse;
import com.school.sba.service.ScheduleService;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/schools/{schoolId}/schedules")
	public ResponseEntity<ResponseStructure<ScheduleResponse>> saveSchedule(@PathVariable("schoolId") int schoolId,
			@RequestBody @Valid ScheduleRequest scheduleRequest) {
		return scheduleService.saveSchedule(schoolId, scheduleRequest);
	}
	
	@GetMapping("schools/{schoolId}/schedules")
	public ResponseEntity<ResponseStructure<ScheduleResponse>> findSchedule(@PathVariable("schoolId") int schoolId){
		return scheduleService.findSchedule(schoolId);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("schedules/{scheduleId}")
	public ResponseEntity<ResponseStructure<ScheduleResponse>> updateSchedule(@PathVariable("scheduleId") int scheduleId,
			@RequestBody @Valid ScheduleRequest scheduleRequest){
		return scheduleService.updateSchedule(scheduleId, scheduleRequest);
	}

}
