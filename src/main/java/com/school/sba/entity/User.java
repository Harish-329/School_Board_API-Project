package com.school.sba.entity;

import java.util.List;

import com.school.sba.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	@Column(unique = true)
	private String userName;
	private String userPassword;
	private String userFirstName;
	private String userLastName;
	
	@Column(unique = true)
	private Long userContact;
	
	@Column(unique = true)
	private String userEmail;
	
	private boolean isDeleted;
	
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	
	@ManyToOne
	@JoinColumn(name = "schoolId")
	private School school;
	
	@ManyToMany(mappedBy = "listOfUsers")
	private List<AcademicProgram> listOfAcademicPrograms;
	
	@ManyToOne
	@JoinColumn(name = "subjectId")
	private Subject subject;
}
