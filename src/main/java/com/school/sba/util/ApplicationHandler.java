package com.school.sba.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.school.sba.exception.AcademicProgramNotAssignedException;
import com.school.sba.exception.AcademicProgramNotFoundException;
import com.school.sba.exception.AdminAlreadyFoundException;
import com.school.sba.exception.AdminCannotBeAssignedToAcademicProgram;
import com.school.sba.exception.AdminCannotBeDeletedException;
import com.school.sba.exception.AdminNotFoundException;
import com.school.sba.exception.ClassCannotAssignedException;
import com.school.sba.exception.ClassHourAlreadyGeneratedException;
import com.school.sba.exception.ClassHourNotFoundException;
import com.school.sba.exception.IdNotFoundException;
import com.school.sba.exception.InvalidBreakTimeException;
import com.school.sba.exception.InvalidClassPeriodEndTimeException;
import com.school.sba.exception.InvalidCloseTimeForScheduleException;
import com.school.sba.exception.InvalidLunchTimeException;
import com.school.sba.exception.InvalidProgramTypeException;
import com.school.sba.exception.InvalidUserRoleException;
import com.school.sba.exception.NoAssociatedObjectsFoundException;
import com.school.sba.exception.OnlyAdminCanCreateSchoolException;
import com.school.sba.exception.OnlyTeacherCanBeAssignedToSubjectException;
import com.school.sba.exception.PreviousClassHourNotFoundException;
import com.school.sba.exception.RoomAlreadyAssignedException;
import com.school.sba.exception.ScheduleAlreadyPresentException;
import com.school.sba.exception.ScheduleNotFoundException;
import com.school.sba.exception.SchoolAlreadyPresentException;
import com.school.sba.exception.SchoolCannotBeCreatedException;
import com.school.sba.exception.SchoolNotFoundException;
import com.school.sba.exception.SubjectCannotBeAssignedToStudentException;
import com.school.sba.exception.SubjectNotAssignedToTeacherException;
import com.school.sba.exception.SubjectNotFoundException;
import com.school.sba.exception.TeacherNotFoundByIdException;
import com.school.sba.exception.UserNotFoundByIdException;

@RestControllerAdvice
public class ApplicationHandler extends ResponseEntityExceptionHandler {

	public ResponseEntity<Object> structure(HttpStatus status, String message, Object rootCause) {
		return new ResponseEntity<Object>(Map.of("status", status.value(), "message", message, "root cause", rootCause),
				status);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<ObjectError> allErrors = ex.getAllErrors();

		Map<String, String> errors = new HashMap<String, String>();

		allErrors.forEach(error -> {

			FieldError fieldError = (FieldError) error;
			errors.put(fieldError.getField(), fieldError.getDefaultMessage());

		});

		return structure(HttpStatus.BAD_REQUEST, "Failed to save the data", errors);

	}

	@ExceptionHandler(SchoolNotFoundException.class)
	public ResponseEntity<Object> handleSchoolNotFoundByIdException(SchoolNotFoundException exception) {
		return structure(HttpStatus.NOT_FOUND, exception.getMessage(), "school not found by the specified school Id");
	}

	@ExceptionHandler(SchoolCannotBeCreatedException.class)
	public ResponseEntity<Object> handleSchoolCannotBeCreatedException(SchoolCannotBeCreatedException exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "school cannot created because school is already present in the database");
	}
	
	@ExceptionHandler(UserNotFoundByIdException.class)
	public ResponseEntity<Object> handleUserNotFoundByIdException(UserNotFoundByIdException exception) {
		return structure(HttpStatus.NOT_FOUND, exception.getMessage(), "user not found by specified user Id");
	}
	
	@ExceptionHandler(ScheduleAlreadyPresentException.class)
	public ResponseEntity<Object> handleScheduleAlreadyPresentException(ScheduleAlreadyPresentException exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "Schedule is already created");
	}
	
	@ExceptionHandler(ScheduleNotFoundException.class)
	public ResponseEntity<Object> handleScheduleNotFoundException(ScheduleNotFoundException exception) {
		return structure(HttpStatus.NOT_FOUND, exception.getMessage(), "Schedule not found by the specified schedule Id");
	}
	
	@ExceptionHandler(AcademicProgramNotFoundException.class)
	public ResponseEntity<Object> handleAcademicProgramNotFoundException(AcademicProgramNotFoundException exception) {
		return structure(HttpStatus.NOT_FOUND, exception.getMessage(), "Academic program not found with the specified academic program Id");
	}
	
	@ExceptionHandler(AdminNotFoundException.class)
	public ResponseEntity<Object> handleAdminNotFoundException(AdminNotFoundException exception) {
		return structure(HttpStatus.NOT_FOUND, exception.getMessage(), "admin not found");
	}
	
	@ExceptionHandler(OnlyAdminCanCreateSchoolException.class)
	public ResponseEntity<Object> handleOnlyAdminCanCreateSchoolException(OnlyAdminCanCreateSchoolException exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "only admin is able to create school");
	}
	
	@ExceptionHandler(AdminCannotBeAssignedToAcademicProgram.class)
	public ResponseEntity<Object> handleAdminCannotBeAssignedToAcademicProgram(AdminCannotBeAssignedToAcademicProgram exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "admin cannot be assigned to academic programs");
	}
	
	@ExceptionHandler(SubjectNotFoundException.class)
	public ResponseEntity<Object> handleSubjectNotFoundException(SubjectNotFoundException exception) {
		return structure(HttpStatus.NOT_FOUND, exception.getMessage(), "subject not found by the specified subject Id");
	}

	@ExceptionHandler(OnlyTeacherCanBeAssignedToSubjectException.class)
	public ResponseEntity<Object> handleOnlyTeacherCanBeAssignedToSubjectException(OnlyTeacherCanBeAssignedToSubjectException exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "subject can only assigned to teacher");
	}
	
	@ExceptionHandler(AdminAlreadyFoundException.class)
	public ResponseEntity<Object> handleAdminAlreadyFoundException(AdminAlreadyFoundException exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "admin is already present");
	}
	
	@ExceptionHandler(SubjectCannotBeAssignedToStudentException.class)
	public ResponseEntity<Object> handleSubjectCannotBeAssignedToStudentException(SubjectCannotBeAssignedToStudentException exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "subject cannot be assigned to student");
	}
	
	@ExceptionHandler(InvalidProgramTypeException.class)
	public ResponseEntity<Object> handleInvalidProgramTypeException(InvalidProgramTypeException exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "program type entered is invalid");
	}
	
	@ExceptionHandler(InvalidUserRoleException.class)
	public ResponseEntity<Object> handleInvalidUserRoleException(InvalidUserRoleException exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "user role entered is invalid");
	}
	
	@ExceptionHandler(TeacherNotFoundByIdException.class)
	public ResponseEntity<Object> handleTeacherNotFoundByIdException(TeacherNotFoundByIdException exception) {
		return structure(HttpStatus.NOT_FOUND, exception.getMessage(), "teacher not found with the specified user Id");
	}
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<Object> handleIdNotFoundException(IdNotFoundException exception) {
		return structure(HttpStatus.NOT_FOUND, exception.getMessage(), "specified id is not found");
	}
	
	@ExceptionHandler(ClassHourNotFoundException.class)
	public ResponseEntity<Object> handleClassHourNotFoundException(ClassHourNotFoundException exception) {
		return structure(HttpStatus.NOT_FOUND, exception.getMessage(), "class hour not found with the specifed Id");
	}
	
	@ExceptionHandler(SubjectNotAssignedToTeacherException.class)
	public ResponseEntity<Object> handleSubjectNotAssignedToTeacherException(SubjectNotAssignedToTeacherException exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "subject is not assigned to teacher");
	}
	
	@ExceptionHandler(SchoolAlreadyPresentException.class)
	public ResponseEntity<Object> handleSchoolAlreadyPresentException(SchoolAlreadyPresentException exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "school already present");
	}
	
	@ExceptionHandler(RoomAlreadyAssignedException.class)
	public ResponseEntity<Object> handleRoomAlreadyAssignedException(RoomAlreadyAssignedException exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "room already reserved for the given time");
	}
	
	@ExceptionHandler(AcademicProgramNotAssignedException.class)
	public ResponseEntity<Object> handleAcademicProgramNotAssignedException(AcademicProgramNotAssignedException exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "academic program is not assigned");
	}
	
	@ExceptionHandler(ClassCannotAssignedException.class)
	public ResponseEntity<Object> handleClassCannotAssignedException(ClassCannotAssignedException exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "class hour, subject and room number cannot be assigned to break time or lunch time");
	}
	
	@ExceptionHandler(NoAssociatedObjectsFoundException.class)
	public ResponseEntity<Object> handleNoAssociatedObjectsFoundException(NoAssociatedObjectsFoundException exception) {
		return structure(HttpStatus.NOT_FOUND, exception.getMessage(), "no associated data found with the specified program id and user role");
	}
	
	@ExceptionHandler(AdminCannotBeDeletedException.class)
	public ResponseEntity<Object> handleAdminCannotBeDeletedException(AdminCannotBeDeletedException exception) {
		return structure(HttpStatus.NOT_FOUND, exception.getMessage(), "admin cannot be deleted");
	}
	
	@ExceptionHandler(ClassHourAlreadyGeneratedException.class)
	public ResponseEntity<Object> handleClassHourAlreadyGeneratedException(ClassHourAlreadyGeneratedException exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "class hour already generated for the week");
	}
	
	@ExceptionHandler(PreviousClassHourNotFoundException.class)
	public ResponseEntity<Object> handlePreviousClassHourNotFoundException(PreviousClassHourNotFoundException exception) {
		return structure(HttpStatus.NOT_FOUND, exception.getMessage(), "previous class hour could not be found");
	}
	
	@ExceptionHandler(InvalidCloseTimeForScheduleException.class)
	public ResponseEntity<Object> handleInvalidCloseTimeForScheduleException(InvalidCloseTimeForScheduleException exception) {
		return structure(HttpStatus.BAD_REQUEST, exception.getMessage(), "closing time input for the schedule is wrong");
	}
	
	@ExceptionHandler(InvalidBreakTimeException.class)
	public ResponseEntity<Object> handleInvalidBreakTimeException(InvalidBreakTimeException exception) {
		return structure(HttpStatus.NOT_FOUND, exception.getMessage(), "break time input for the schedule is wrong");
	}
	
	@ExceptionHandler(InvalidLunchTimeException.class)
	public ResponseEntity<Object> handleInvalidLunchTimeException(InvalidLunchTimeException exception) {
		return structure(HttpStatus.NOT_FOUND, exception.getMessage(), "lunch time input for the schedule is wrong");
	}
	
	@ExceptionHandler(InvalidClassPeriodEndTimeException.class)
	public ResponseEntity<Object> handleInvalidClassPeriodEndTimeException(InvalidClassPeriodEndTimeException exception) {
		return structure(HttpStatus.NOT_FOUND, exception.getMessage(), "invalid class ending time");
	}
}
