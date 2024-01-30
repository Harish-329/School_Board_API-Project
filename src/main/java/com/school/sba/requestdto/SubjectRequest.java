package com.school.sba.requestdto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequest {
	private List<String> subjectNames;

	@Override
	public String toString() {
		StringBuilder a = new StringBuilder("");
		
		for(String s : subjectNames) {
			a.append(s+" ");
		}
		
		String ss = a.toString();
		
		return ss;
	}
	
	
}
