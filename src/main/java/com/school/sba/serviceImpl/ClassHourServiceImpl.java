package com.school.sba.serviceImpl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.AcademicProgram;
import com.school.sba.entity.ClassHour;
import com.school.sba.entity.Schedule;
import com.school.sba.entity.School;
import com.school.sba.entity.Subject;
import com.school.sba.entity.User;
import com.school.sba.enums.ClassStatus;
import com.school.sba.enums.UserRole;
import com.school.sba.exception.AcademicProgramNotAssignedException;
import com.school.sba.exception.AcademicProgramNotFoundException;
import com.school.sba.exception.ClassCannotAssignedException;
import com.school.sba.exception.ClassHourNotFoundException;
import com.school.sba.exception.IdNotFoundException;
import com.school.sba.exception.RoomAlreadyAssignedException;
import com.school.sba.exception.ScheduleNotFoundException;
import com.school.sba.exception.SubjectNotAssignedToTeacherException;
import com.school.sba.exception.SubjectNotFoundException;
import com.school.sba.exception.TeacherNotFoundByIdException;
import com.school.sba.repository.AcademicProgramRepository;
import com.school.sba.repository.ClassHourRepository;
import com.school.sba.repository.SubjectRepository;
import com.school.sba.repository.UserRepository;
import com.school.sba.requestdto.ClassHourRequest;
import com.school.sba.responsedto.ClassHourResponse;
import com.school.sba.service.ClassHourService;
import com.school.sba.util.ResponseEntityProxy;
import com.school.sba.util.ResponseStructure;

@Service
public class ClassHourServiceImpl implements ClassHourService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private ClassHourRepository classHourRepository;

	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	private List<ClassHourResponse> mapToClassHourResponse(List<ClassHour> savedList) {

		List<ClassHourResponse> listOfClassHourResponses = new ArrayList<>();
		savedList.forEach(classHour -> {
			listOfClassHourResponses
			.add(ClassHourResponse.builder()
					.classBeginsAt(classHour.getClassBeginsAt().toLocalTime())
					.classEndsAt(classHour.getClassEndsAt().toLocalTime())
					.classRoomNumber(classHour.getClassRoomNumber())
					.classStatus(classHour.getClassStatus())
					.day(classHour.getClassBeginsAt().getDayOfWeek())
					.date(classHour.getClassBeginsAt().toLocalDate())
					.build());
		});

		return listOfClassHourResponses;

	}

	private boolean isRoomReserved(LocalDateTime classBeginsAt,LocalDateTime classEndsAt, int classRoomNumber) {
		return classHourRepository.existsByClassBeginsAtAndClassRoomNumber(classBeginsAt, classRoomNumber);
	}

	private boolean isBreakTime(LocalDateTime currentTime, LocalTime breakStartTime, LocalTime breakEndTime) {
		return  currentTime.toLocalTime().equals(breakStartTime) ||
				(currentTime.toLocalTime().isAfter(breakStartTime) && currentTime.toLocalTime().isBefore(breakEndTime));

	}

	private boolean isLunchTime(LocalDateTime currentTime, LocalTime lunchStartTime, LocalTime lunchEndTime) {
		return currentTime.toLocalTime().equals(lunchStartTime) ||
				(currentTime.toLocalTime().isAfter(lunchStartTime) && currentTime.toLocalTime().isBefore(lunchEndTime));

	}

	public List<ClassHour> generateClassHour(AcademicProgram academicProgram) {

		List<ClassHour> listOfClassHour = new ArrayList<ClassHour>();

		School school = academicProgram.getSchool();
		Schedule schedule = school.getSchedule();

		if (schedule != null) {

			int weekOffDay = school.getWeekOffDay().getValue();
			int startingDayOfWeek;

			if(weekOffDay == 7) 
				startingDayOfWeek = 1;
			else
				startingDayOfWeek = weekOffDay+1;

			int classHoursPerDay = schedule.getClassHoursPerDay();
			int classHourLengthInMinutes = (int) schedule.getClassHoursLengthInMinutes().toMinutes();

			int currentDayNum = LocalDateTime.now().toLocalDate().getDayOfWeek().getValue();
			int diff = currentDayNum - startingDayOfWeek;

			LocalDateTime currentTime = LocalDateTime.now().minusDays(diff).with(schedule.getOpensAt());

			LocalTime breakTimeStart = schedule.getBreakTime();
			LocalTime breakTimeEnd = schedule.getBreakTime().plusMinutes(schedule.getBreakLengthInMinutes().toMinutes());
			LocalTime lunchTimeStart = schedule.getLunchTime();
			LocalTime lunchTimeEnd = schedule.getLunchTime().plusMinutes(schedule.getLunchLengthInMinutes().toMinutes());

			for (int day = 1; day <= 6; day++) {

				for (int hour = 0; hour < classHoursPerDay + 2; hour++) {

					ClassHour classHour = new ClassHour();

					if (isLunchTime(currentTime, lunchTimeStart, lunchTimeEnd)) {

						classHour.setClassBeginsAt(currentTime);
						classHour.setClassEndsAt(LocalDateTime.now().with(lunchTimeEnd));
						classHour.setClassStatus(ClassStatus.LUNCH_TIME);
						currentTime = currentTime.plusMinutes(schedule.getLunchLengthInMinutes().toMinutes());

					}
					else {
						if (isBreakTime(currentTime, breakTimeStart, breakTimeEnd)) {

							classHour.setClassBeginsAt(currentTime);
							classHour.setClassEndsAt(LocalDateTime.now().with(breakTimeEnd));

							classHour.setClassStatus(ClassStatus.BREAK_TIME);
							currentTime = currentTime.plusMinutes(schedule.getBreakLengthInMinutes().toMinutes());

						} else {

							LocalDateTime beginsAt = currentTime;
							LocalDateTime endsAt = beginsAt.plusMinutes(classHourLengthInMinutes);

							classHour.setClassBeginsAt(beginsAt);
							classHour.setClassEndsAt(endsAt);
							classHour.setClassStatus(ClassStatus.NOT_SCHEDULED);

							currentTime = endsAt;
						}

					}

					classHour.setAcademicPrograms(academicProgram);
					listOfClassHour.add(classHour);
				}

				currentTime = currentTime.plusDays(1).with(schedule.getOpensAt());

			}
		} else {

			throw new ScheduleNotFoundException("schedule not found");

		}

		return listOfClassHour;

	}



	@Override
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> addClassHour(int programId) {
		return academicProgramRepository.findById(programId)
				.map(academicProgram -> {

					List<ClassHour> listOfClassHours = generateClassHour(academicProgram);

					listOfClassHours = classHourRepository.saveAll(listOfClassHours);

					return ResponseEntityProxy.setResponseStructure(HttpStatus.CREATED,
							"class hour generated successfully",
							mapToClassHourResponse(listOfClassHours));

				}).orElseThrow(() -> new AcademicProgramNotFoundException("academic program not found"));
	}


	@Override
	public ResponseEntity<ResponseStructure<List<ClassHourResponse>>> updateClassHour(
			List<ClassHourRequest> listOfClassHourRequest) {

		List<ClassHour> listOfClassHour = new ArrayList<ClassHour>();

		for(ClassHourRequest classHourRequest : listOfClassHourRequest) {

			ClassHour classHour = classHourRepository.findById(classHourRequest.getClassHourId())
					.orElseThrow(() -> new ClassHourNotFoundException("class not found"));

			User teacher = userRepository.findById(classHourRequest.getTeacherId())
					.orElseThrow(() -> new IdNotFoundException("id not found"));

			Subject subject = subjectRepository.findById(classHourRequest.getSubjectId())
					.orElseThrow(() -> new SubjectNotFoundException("subject not found"));


			if(teacher.getUserRole().equals(UserRole.TEACHER)) {

				if(teacher.getSubject().equals(subject)){

					if(teacher.getListOfAcademicPrograms().contains(classHour.getAcademicPrograms())) {

						LocalDateTime classBeginsAt = classHour.getClassBeginsAt();
						LocalDateTime classEndsAt = classHour.getClassEndsAt();

						LocalDateTime currentDateTime = LocalDateTime.now();

						if(isRoomReserved(classBeginsAt,classEndsAt, classHourRequest.getClassRoomNumber())) {				
							throw new RoomAlreadyAssignedException("room already reserved");
						}

						if(!classHour.getClassStatus().equals(ClassStatus.BREAK_TIME) &&
								
								!classHour.getClassStatus().equals(ClassStatus.LUNCH_TIME)) {

							if(currentDateTime.isAfter(classBeginsAt) && currentDateTime.isBefore(classEndsAt)) {
								classHour.setUser(teacher); 
								classHour.setClassRoomNumber(classHourRequest.getClassRoomNumber());
								classHour.setSubject(subject);
								classHour.setClassStatus(ClassStatus.ONGOING);
							}
							else if(currentDateTime.isBefore(classBeginsAt)){
								classHour.setUser(teacher);
								classHour.setClassRoomNumber(classHourRequest.getClassRoomNumber());
								classHour.setSubject(subject);
								classHour.setClassStatus(ClassStatus.UPCOMING);
							}
							else {
								classHour.setUser(teacher);
								classHour.setClassRoomNumber(classHourRequest.getClassRoomNumber());
								classHour.setSubject(subject);
								classHour.setClassStatus(ClassStatus.COMPLETED);
							}
							listOfClassHour.add(classHour);	
							classHourRepository.save(classHour);
						}
						else {
							throw new ClassCannotAssignedException("class hour cannot be assiged to break time or lunch time");
						}
					}
					else {
						throw new AcademicProgramNotAssignedException("academic program not assigned");
					}
				}
				else {
					throw new SubjectNotAssignedToTeacherException("subject not assigned");
				}
			}
			else {
				throw new TeacherNotFoundByIdException("teacher not found");
			}
		}
		return ResponseEntityProxy.setResponseStructure(HttpStatus.OK,
				"class hour updated successfully",
				mapToClassHourResponse(listOfClassHour));
	}

}
