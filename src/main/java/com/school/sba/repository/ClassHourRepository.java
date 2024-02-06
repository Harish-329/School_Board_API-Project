package com.school.sba.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.sba.entity.AcademicProgram;
import com.school.sba.entity.ClassHour;
import com.school.sba.entity.User;

public interface ClassHourRepository extends JpaRepository<ClassHour, Integer> {

	boolean existsByClassBeginsAtAndClassRoomNumber(LocalDateTime classBeginsAt, int classRoomNumber);

	List<ClassHour> findByUser(User user);

	List<ClassHour> findByClassBeginsAtAfter(LocalDateTime firstDayOfPreviousWeek);

	boolean existsByClassBeginsAt(LocalDateTime with);

	List<ClassHour> findByAcademicProgramsAndClassBeginsAtBetween(AcademicProgram academicProgram, LocalDateTime atStartOfDay,
			LocalDateTime atStartOfDay2);

}
