package com.school.sba.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sba.requestdto.AcademicProgramRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.util.ResponseStructure;

public interface AcademicProgramService {

	ResponseEntity<ResponseStructure<AcademicProgramResponse>> createProgram(int schoolId,
			AcademicProgramRequest academicProgramRequest);

	ResponseEntity<ResponseStructure<List<AcademicProgramResponse>>> findAllAcademicProgram(int schoolId);

	ResponseEntity<ResponseStructure<AcademicProgramResponse>> softDeleteAcademicProgram(int programId);

	ResponseEntity<ResponseStructure<AcademicProgramResponse>> getAcademicProgram(int programId);

	ResponseEntity<ResponseStructure<AcademicProgramResponse>> autoGenerateButton(int programId);

}
