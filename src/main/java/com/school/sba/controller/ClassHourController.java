package com.school.sba.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.school.sba.requestdto.ClassHourRequest;
import com.school.sba.requestdto.ExcelRequest;
import com.school.sba.responsedto.ClassHourResponse;
import com.school.sba.service.ClassHourService;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class ClassHourController {
	
	@Autowired
	private ClassHourService classHourService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/academic-programs/{programId}/class-hours")
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> addClassHour(@PathVariable("programId") int programId){
		return classHourService.addClassHour(programId);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/class-hours")
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> updateClassHour(@RequestBody List<ClassHourRequest> classHourRequest){
		return classHourService.updateClassHour(classHourRequest);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/academic-programs/{programId}/class-hours")
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> generateClassHourForNextWeek(@PathVariable("programId") int programId){
		return classHourService.generateClassHourForNextWeek(programId);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/academic-programs/{programId}/class-hours/write-excel")
	public ResponseEntity<ResponseStructure<String>> writeExcelSheet(@PathVariable("programId") int programId,
			@RequestBody @Valid ExcelRequest excelRequest) {
		return classHourService.writeExcelSheet(programId, excelRequest);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/academic-programs/{programId}/class-hours/from/{fromDate}/to/{toDate}/write-excel")
	public ResponseEntity<?> writeToExcel(@PathVariable("programId") int programId,
			@PathVariable("fromDate") LocalDate fromDate,
			@PathVariable("toDate") LocalDate toDate,
			@RequestParam("file") MultipartFile file) {
		return classHourService.writeToExcel(programId, fromDate, toDate, file);
	}

}
