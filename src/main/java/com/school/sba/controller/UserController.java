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

import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.service.UserService;
import com.school.sba.util.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserResponse>> registerAdmin(@Valid @RequestBody UserRequest userRequest) {
		return userService.registerAdmin(userRequest);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/users")
	public ResponseEntity<ResponseStructure<UserResponse>> addOtherUser(@RequestBody @Valid UserRequest userRequest) {
		return userService.addOtherUser(userRequest);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUser(@PathVariable("userId") int userId) {
		return userService.findUser(userId);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> softDeleteUser(@PathVariable("userId") int userId) {
		return userService.softDeleteUser(userId);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(@PathVariable("userId") int userId,
			@RequestBody @Valid UserRequest userRequest) {
		return userService.updateUser(userId, userRequest);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/academic-programs/{programId}/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> assignToAcademicProgram(
			@PathVariable("programId") int programId, @PathVariable("userId") int userId) {
		return userService.assignToAcademicProgram(programId, userId);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/subjects/{subjectId}/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> assignSubjectToTeacher(
			@PathVariable("subjectId") int subjectId, @PathVariable("userId") int userId) {
		return userService.assignSubjectToTeacher(subjectId, userId);
	}

	@PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('TEACHER')")
	@GetMapping("/academic-programs/{programId}/user-roles/{role}/users")
	public ResponseEntity<ResponseStructure<List<UserResponse>>> findAllByRole(@PathVariable("programId") int programId,
			@PathVariable("role") String userRole) {
		return userService.findAllByRole(programId, userRole);
	}
	
}
