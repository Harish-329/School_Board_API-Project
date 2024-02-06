package com.school.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.service.SchoolService;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class SchoolController {
	
	@Autowired
	private SchoolService schoolService;
		
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/users/schools")
	public ResponseEntity<ResponseStructure<SchoolResponse>> createSchool( @RequestBody @Valid SchoolRequest schoolRequest){
		return schoolService.createSchool(schoolRequest);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<SchoolResponse>> updateSchool(@PathVariable("schoolId") int schoolId, @RequestBody @Valid SchoolRequest schoolRequest){
		return schoolService.updateSchool(schoolId, schoolRequest);
	}
	
	@GetMapping("/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<SchoolResponse>> findSchool(@PathVariable("schoolId") int schoolId){
		return schoolService.findSchool(schoolId);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/schools/{schoolId}")
	public ResponseEntity<ResponseStructure<SchoolResponse>> softDeleteSchool(@PathVariable("schoolId") int schoolId){
		return schoolService.softDeleteSchool(schoolId);
	}


	
}
