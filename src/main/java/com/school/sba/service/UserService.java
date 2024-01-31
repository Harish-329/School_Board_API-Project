package com.school.sba.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

public interface UserService {

	ResponseEntity<ResponseStructure<UserResponse>> registerAdmin(UserRequest userRequest);

	ResponseEntity<ResponseStructure<UserResponse>> findUser(Integer userId);

	ResponseEntity<ResponseStructure<UserResponse>> softDeleteUser(int userId);

	ResponseEntity<ResponseStructure<UserResponse>> updateUser(int userId, UserRequest userRequest);

	ResponseEntity<ResponseStructure<UserResponse>> assignToAcademicProgram(int programId, int userId);

	ResponseEntity<ResponseStructure<UserResponse>> assignSubjectToTeacher(int subjectId, int userId);

	ResponseEntity<ResponseStructure<UserResponse>> addOtherUser(UserRequest userRequest);

	ResponseEntity<ResponseStructure<List<UserResponse>>> findAllByRole(int programId, String userRole);


}
