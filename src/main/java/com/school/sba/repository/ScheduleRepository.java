package com.school.sba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.sba.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

}
