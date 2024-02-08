package com.school.sba.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.sba.entity.AcademicProgram;

public interface AcademicProgramRepository extends JpaRepository<AcademicProgram, Integer>{

	List<AcademicProgram> findByIsDeleted(boolean b);	
	
}
