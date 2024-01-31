package com.school.sba.serviceImpl;

import java.time.DayOfWeek;
import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.school.sba.entity.School;
import com.school.sba.enums.UserRole;
import com.school.sba.exception.InvalidWeekDayException;
import com.school.sba.exception.SchoolAlreadyPresentException;
import com.school.sba.exception.SchoolCannotBeCreatedException;
import com.school.sba.exception.SchoolNotFoundException;
import com.school.sba.exception.UserNotFoundByIdException;
import com.school.sba.repository.AcademicProgramRepository;
import com.school.sba.repository.ScheduleRepository;
import com.school.sba.repository.SchoolRepository;
import com.school.sba.repository.UserRepository;
import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.service.SchoolService;
import com.school.sba.util.ResponseEntityProxy;
import com.school.sba.util.ResponseStructure;

import jakarta.transaction.Transactional;


@Service
public class SchoolServiceImpl implements SchoolService{

	@Autowired
	private SchoolRepository schoolRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	private School mapToSchool(SchoolRequest schoolRequest) {
		return School.builder()
				.schoolName(schoolRequest.getSchoolName())
				.schoolEmailId(schoolRequest.getSchoolEmailId())
				.schoolContactNumber(schoolRequest.getSchoolContactNumber())
				.schoolAddress(schoolRequest.getSchoolAddress())
				.weekOffDay(DayOfWeek.valueOf(schoolRequest.getWeekOffDay().toUpperCase()))
				.build();
	}

	private SchoolResponse mapToSchoolResponse(School school) {
		return SchoolResponse.builder()
				.schoolId(school.getSchoolId())
				.schoolName(school.getSchoolName())
				.schoolEmailId(school.getSchoolEmailId())
				.schoolContactNumber(school.getSchoolContactNumber())
				.schoolAddress(school.getSchoolAddress())
				.weekOfDay(school.getWeekOffDay())
				.build();
	}

	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> createSchool(SchoolRequest schoolRequest){

		String username = SecurityContextHolder.getContext()
				.getAuthentication()
				.getName();

		return userRepo.findByUserName(username)
				.map(user -> {

					if(schoolRepo.existsByIsDeleted(false)) {
						throw new SchoolAlreadyPresentException("school already exist");
					}

					DayOfWeek weekOffDay = DayOfWeek.valueOf(schoolRequest.getWeekOffDay().toUpperCase());
					if(!EnumSet.allOf(DayOfWeek.class).contains(weekOffDay))
						throw new InvalidWeekDayException("invalid week day");

					user.setSchool(null);

					if(user.getUserRole().equals(UserRole.ADMIN)) {
						if(user.getSchool() == null) {
							School school = schoolRepo.save(mapToSchool(schoolRequest));

							userRepo.findAll().forEach(userFromRepo -> {
								userFromRepo.setSchool(school);
								userRepo.save(user);
							});

							return ResponseEntityProxy.setResponseStructure(HttpStatus.CREATED,
									"School inserted successfully",
									mapToSchoolResponse(school));
						}
						else {
							throw new SchoolCannotBeCreatedException("school is already present");
						}
					}
					else {
						throw new SchoolCannotBeCreatedException("school can be created only by ADMIN");
					}
				})
				.orElseThrow(() -> new UserNotFoundByIdException("user not found"));

	}



	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> updateSchool(int schoolId, SchoolRequest schoolRequest){

		return schoolRepo.findById(schoolId)
				.map(school -> {
					DayOfWeek weekOffDay = DayOfWeek.valueOf(schoolRequest.getWeekOffDay().toUpperCase());
					if(!EnumSet.allOf(DayOfWeek.class).contains(weekOffDay))
						throw new InvalidWeekDayException("invalid week day");

					school = mapToSchool(schoolRequest);
					school.setSchoolId(school.getSchoolId());
					school = schoolRepo.save(school);

					return ResponseEntityProxy.setResponseStructure(HttpStatus.OK,
							"School updated successfully",
							mapToSchoolResponse(school));
				})
				.orElseThrow(() -> new SchoolNotFoundException("school not found"));

	}


	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> findSchool(int schoolId){

		return schoolRepo.findById(schoolId)
				.map(school -> {
					return ResponseEntityProxy.setResponseStructure(HttpStatus.FOUND,
							"school found successfully",
							mapToSchoolResponse(school));
				})
				.orElseThrow(() -> new SchoolNotFoundException("school not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> softDeleteSchool(int schoolId) {

		return schoolRepo.findById(schoolId)	
				.map(school -> {
					if(school.isDeleted() == true)
						throw new SchoolNotFoundException("school not found");

					school.setDeleted(true);
					schoolRepo.save(school);


					return ResponseEntityProxy.setResponseStructure(HttpStatus.OK,
							"school deleted successfully", 
							mapToSchoolResponse(school));

				})
				.orElseThrow(() -> new SchoolNotFoundException("school not found"));
	}

	@Transactional
	public void hardDeleteSchool() {
		
		schoolRepo.findByIsDeleted(true).forEach(school -> {
			
			academicProgramRepository.deleteAll(school.getListOfAcademicPrograms());
			
			userRepo.deleteAll(userRepo.findBySchool(school));
			
			schoolRepo.delete(school);
		});
		
	}


}
