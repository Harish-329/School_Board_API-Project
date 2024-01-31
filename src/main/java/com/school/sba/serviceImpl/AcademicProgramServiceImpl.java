package com.school.sba.serviceImpl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.AcademicProgram;
import com.school.sba.entity.Subject;
import com.school.sba.enums.ProgramType;
import com.school.sba.exception.AcademicProgramNotFoundException;
import com.school.sba.exception.InvalidProgramTypeException;
import com.school.sba.exception.SchoolNotFoundException;
import com.school.sba.repository.AcademicProgramRepository;
import com.school.sba.repository.ClassHourRepository;
import com.school.sba.repository.SchoolRepository;
import com.school.sba.requestdto.AcademicProgramRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.service.AcademicProgramService;
import com.school.sba.util.ResponseEntityProxy;
import com.school.sba.util.ResponseStructure;

import jakarta.transaction.Transactional;


@Service
public class AcademicProgramServiceImpl implements AcademicProgramService{

	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	@Autowired
	private SchoolRepository schoolRepository;
	
	@Autowired
	private ClassHourRepository classHourRepository;

	public AcademicProgramResponse mapToAcademicProgramResponse(AcademicProgram academicProgram) {

		List<String> subjects = new ArrayList<String>();

		List<Subject> listOfSubject = academicProgram.getListOfSubject();

		if(listOfSubject != null) {
			listOfSubject.forEach(sub -> {
				subjects.add(sub.getSubjectName());
			});
		}

		return AcademicProgramResponse.builder()
				.programId(academicProgram.getProgramId())
				.programType(academicProgram.getProgramType())
				.programName(academicProgram.getProgramName())
				.programBeginsAt(academicProgram.getProgramBeginsAt())
				.programEndsAt(academicProgram.getProgramEndsAt())
				.listOfSubjects(subjects)
				.build();
	}

	private AcademicProgram mapToAcademicProgram(AcademicProgramRequest academicProgramRequest) {
		return AcademicProgram.builder()
				.programType(ProgramType.valueOf(academicProgramRequest.getProgramType().toUpperCase()))
				.programName(academicProgramRequest.getProgramName())
				.programBeginsAt(academicProgramRequest.getProgramBeginsAt())
				.programEndsAt(academicProgramRequest.getProgramEndsAt())
				.build();
	}

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> createProgram(int schoolId,
			AcademicProgramRequest academicProgramRequest) {

		ProgramType programType = ProgramType.valueOf(academicProgramRequest.getProgramType().toUpperCase());
		if(!EnumSet.allOf(ProgramType.class).contains(programType))
			throw new InvalidProgramTypeException("invalid program type");

		return schoolRepository.findById(schoolId)
				.map(school -> {
					AcademicProgram academicProgram = academicProgramRepository.save(mapToAcademicProgram(academicProgramRequest));

					school.getListOfAcademicPrograms().add(academicProgram);

					school = schoolRepository.save(school);
					academicProgram.setSchool(school);

					academicProgram = academicProgramRepository.save(academicProgram);

					return ResponseEntityProxy.setResponseStructure(HttpStatus.CREATED,
							"Academic program created successfully",
							mapToAcademicProgramResponse(academicProgram));

				})
				.orElseThrow(() -> new SchoolNotFoundException("school not found"));

	}

	@Override
	public ResponseEntity<ResponseStructure<List<AcademicProgramResponse>>> findAllAcademicProgram(int schoolId) {

		return schoolRepository.findById(schoolId)
				.map(school -> {
					List<AcademicProgram> listOfAcadmicProgram = academicProgramRepository.findAll();

					List<AcademicProgramResponse> listOfAcademicProgramResponse = listOfAcadmicProgram.stream()
							.map(this::mapToAcademicProgramResponse)
							.collect(Collectors.toList());

					if(listOfAcadmicProgram.isEmpty()) {

						return ResponseEntityProxy.setResponseStructure(HttpStatus.NO_CONTENT,
								"no programs found",
								listOfAcademicProgramResponse);
					}
					else {

						return ResponseEntityProxy.setResponseStructure(HttpStatus.FOUND,
								"found list of academic programs",
								listOfAcademicProgramResponse);
					}
				})
				.orElseThrow(() -> new SchoolNotFoundException("school not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> softDeleteAcademicProgram(int programId) {
		
		return academicProgramRepository.findById(programId)
				.map(academicProgram -> {
					if(academicProgram.isDeleted()==true)
						throw new AcademicProgramNotFoundException("academic program not found");
					
					academicProgram.setDeleted(true);
					academicProgramRepository.save(academicProgram);

					return ResponseEntityProxy.setResponseStructure(HttpStatus.OK,
							"academic program deleted successfully",
							mapToAcademicProgramResponse(academicProgram));
				})
				.orElseThrow(() -> new AcademicProgramNotFoundException("academic program not found"));
	}

	@Transactional
	public void hardDeleteAcademicProgram() {
		
		academicProgramRepository.findByIsDeleted(true).forEach(academicProgram -> {
			classHourRepository.deleteAll(academicProgram.getListOfClassHours());
			
			academicProgramRepository.delete(academicProgram);
		});
		
	}


}
