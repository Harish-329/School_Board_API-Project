package com.school.sba.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.sba.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{

}
