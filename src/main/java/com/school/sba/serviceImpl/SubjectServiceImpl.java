package com.school.sba.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.Subject;
import com.school.sba.exception.AcademicProgramNotFoundException;
import com.school.sba.repository.AcademicProgramRepository;
import com.school.sba.repository.SubjectRepository;
import com.school.sba.requestdto.SubjectRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.responsedto.SubjectResponse;
import com.school.sba.service.SubjectService;
import com.school.sba.util.ResponseEntityProxy;
import com.school.sba.util.ResponseStructure;

@Service
public class SubjectServiceImpl implements SubjectService{

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	@Autowired
	private AcademicProgramServiceImpl academicProgramServiceImpl;

	private List<SubjectResponse> mapTOListOfSubjectResponse(List<Subject> listOfSubjects) {
		List<SubjectResponse> listOfSubjectResponse = new ArrayList<>();

		listOfSubjects.forEach(subject -> {
			SubjectResponse sr = new SubjectResponse();
			sr.setSubjectId(subject.getSubjectId());
			sr.setSubjectNames(subject.getSubjectName());
			listOfSubjectResponse.add(sr);
		});

		return listOfSubjectResponse;
	}

	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> addSubject(int programId, SubjectRequest subjectRequest) {
		return academicProgramRepository.findById(programId)
				.map(academicProgram -> {
					List<Subject> listOfSubjects = (academicProgram.getListOfSubject() != null) ? academicProgram.getListOfSubject() : new ArrayList<Subject>();


					// to add the new project that are specified by the client
					subjectRequest.getSubjectNames().forEach(name -> {
						boolean isPresent = false;
						for(Subject subject : listOfSubjects) {
							isPresent = (name.equalsIgnoreCase(subject.getSubjectName())) ? true : false;
							if(isPresent) break;
						}
						if(!isPresent) {
							listOfSubjects.add(subjectRepository.findBySubjectName(name)
									.orElseGet(() -> subjectRepository.save(Subject.builder().subjectName(name).build())));
						}
					});


					//to remove the subject that are not specified by the client
					List<Subject> toBeRemoved = new ArrayList<Subject>();
					listOfSubjects.forEach(subject -> {
						boolean isPresent = false;
						for(String name : subjectRequest.getSubjectNames()) {
							isPresent = (subject.getSubjectName().equalsIgnoreCase(name)) ? true : false;
							if(isPresent) break;
						}
						if(!isPresent) toBeRemoved.add(subject);
					});

					listOfSubjects.removeAll(toBeRemoved);

					academicProgram.setListOfSubject(listOfSubjects);

					academicProgramRepository.save(academicProgram);
					
					return ResponseEntityProxy.setResponseStructure(HttpStatus.CREATED,
							"subjects have been updated successfully",
							academicProgramServiceImpl.mapToAcademicProgramResponse(academicProgram));
				})
				.orElseThrow(() -> new AcademicProgramNotFoundException("academic program not found"));

	}

	@Override
	public ResponseEntity<ResponseStructure<List<SubjectResponse>>> findAllSubjects() {
		List<Subject> listOfSubjects = subjectRepository.findAll();

		if(listOfSubjects.isEmpty()) {
			
			return ResponseEntityProxy.setResponseStructure(HttpStatus.NOT_FOUND,
					"No subjects found",
					mapTOListOfSubjectResponse(listOfSubjects));
		}
		else {
			
			return ResponseEntityProxy.setResponseStructure(HttpStatus.FOUND,
					"subjects found",
					mapTOListOfSubjectResponse(listOfSubjects));
		}

	}




}
