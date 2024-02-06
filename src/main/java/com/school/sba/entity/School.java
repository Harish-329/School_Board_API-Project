	package com.school.sba.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class School {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int schoolId;
	private String schoolName;
	@Column(unique = true)
	private long schoolContactNumber;
	@Column(unique = true)
	private String schoolEmailId;
	@Column(unique = true)
	private String schoolAddress;
	
	private boolean isDeleted;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	private Schedule schedule;
	
	@OneToMany(mappedBy = "school")
	private List<AcademicProgram> listOfAcademicPrograms;
	
}
