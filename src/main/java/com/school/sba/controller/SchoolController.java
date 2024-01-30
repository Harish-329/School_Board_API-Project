package com.school.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.service.SchoolService;
import com.school.sba.util.ResponseStructure;

@RestController
public class SchoolController {
	
	@Autowired
	private SchoolService schoolService;
		
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/users/schools")
	public ResponseEntity<ResponseStructure<SchoolResponse>> createSchool( @RequestBody SchoolRequest schoolRequest){
		return schoolService.createSchool(schoolRequest);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<SchoolResponse>> updateSchool(@RequestBody SchoolRequest schoolRequest){
		return schoolService.updateSchool(schoolRequest);
	}
	
	@GetMapping("/schools")
	public ResponseEntity<ResponseStructure<SchoolResponse>> findSchool(){
		return schoolService.findSchool();
	}


	
}
