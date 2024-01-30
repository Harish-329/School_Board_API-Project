package com.school.sba.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityProxy {
	public static <T> ResponseEntity<ResponseStructure<T>> setResponseStructure(HttpStatus status, String message, T data){
		ResponseStructure<T> structure = new ResponseStructure<T>();
		
		structure.setStatus(status.value());
		structure.setMessage(message);
		structure.setData(data);
		
		return new ResponseEntity<ResponseStructure<T>>(structure, status);
	}
}
