package com.school.sba.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sba.entity.ClassHour;

@Repository
public interface ClassHourRepository extends JpaRepository<ClassHour, Integer> {

//	boolean existsByClassBeginsAtBetweenClassRoomNumber(LocalDateTime classBeginsAt,
//			LocalDateTime classEndsAt, int classRoomNumber);

	boolean existsByClassBeginsAtAndClassRoomNumber(LocalDateTime classBeginsAt, int classRoomNumber);



}
