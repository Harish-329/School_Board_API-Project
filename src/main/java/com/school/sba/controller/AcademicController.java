package com.school.sba.controller;

import java.util.List;

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

import com.school.sba.requestdto.AcademicProgramRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.service.AcademicProgramService;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class AcademicController {
	
	@Autowired
	private AcademicProgramService academicProgramService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/schools/{schoolId}/academic-programs")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> createProgram(@PathVariable("schoolId") int schoolId,
			@RequestBody @Valid AcademicProgramRequest academicProgramRequest){
		return academicProgramService.createProgram(schoolId, academicProgramRequest);
	}
	
	@GetMapping("/schools/{schoolId}/academic-programs")
	public ResponseEntity<ResponseStructure<List<AcademicProgramResponse>>> findAllAcademicProgram(@PathVariable("schoolId") int schoolId){
		return academicProgramService.findAllAcademicProgram(schoolId);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("academic-programs/{programId}")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> softDeleteAcademicProgram(@PathVariable("programId") int programId){
		return academicProgramService.softDeleteAcademicProgram(programId);
	}
	
	@GetMapping("academic-programs/{programId}")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> getAcademicProgram(@PathVariable("programId") int programId){
		return academicProgramService.getAcademicProgram(programId);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("auto-generate/{programId}/academic-programs")
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> autoGenerateButton(@PathVariable("programId") int programId){
		return academicProgramService.autoGenerateButton(programId);
	}
}
