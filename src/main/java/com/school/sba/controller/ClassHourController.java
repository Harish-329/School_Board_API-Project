package com.school.sba.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@RestController
public class ClassHourController {
	
	@Autowired
	private ClassHourService classHourService;
	
	@PostMapping("/academic-programs/{programId}/class-hours")
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> addClassHour(@PathVariable("programId") int programId){
		return classHourService.addClassHour(programId);
	}
	
	@PutMapping("/class-hours")
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> updateClassHour(@RequestBody List<ClassHourRequest> classHourRequest){
		return classHourService.updateClassHour(classHourRequest);
	}
	
	@PutMapping("/academic-programs/{programId}/class-hours")
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> generateClassHourForNextWeek(@PathVariable("programId") int programId){
		return classHourService.generateClassHourForNextWeek(programId);
	}

	@PostMapping("/academic-programs/{programId}/class-hours/write-excel")
	public ResponseEntity<ResponseStructure<String>> writeExcelSheet(@PathVariable("programId") int programId,
			@RequestBody ExcelRequest excelRequest) {
		return classHourService.writeExcelSheet(programId, excelRequest);
	}
	
	@PostMapping("/academic-programs/{programId}/class-hours/from/{fromDate}/to/{toDate}/write-excel")
	public ResponseEntity<?> writeToExcel(@PathVariable("programId") int programId,
			@PathVariable("fromDate") LocalDate fromDate,
			@PathVariable("toDate") LocalDate toDate,
			@RequestParam("file") MultipartFile file) {
		return classHourService.writeToExcel(programId, fromDate, toDate, file);
	}

}
