package com.school.sba.util;

import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.school.sba.repository.AcademicProgramRepository;
import com.school.sba.serviceImpl.AcademicProgramServiceImpl;
import com.school.sba.serviceImpl.ClassHourServiceImpl;
import com.school.sba.serviceImpl.SchoolServiceImpl;
import com.school.sba.serviceImpl.UserServiceImpl;

@Component
public class ScheduledJobs {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private AcademicProgramServiceImpl academicProgramServiceImpl;

	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	@Autowired
	private ClassHourServiceImpl classHourServiceImpl;

	@Autowired
	private SchoolServiceImpl schoolServiceImpl;

	@Scheduled(cron = "0 0 0 * * MON")
	public void hardDelete() {
		userServiceImpl.hardDeleteUser();
		academicProgramServiceImpl.hardDeleteAcademicProgram();
		schoolServiceImpl.hardDeleteSchool();
	}

	@Scheduled(cron = "0 0 0 * * MON")
	public void generateClassHourEveryWeek() {
		academicProgramRepository.findAll().forEach(academicProgram -> {
			if (academicProgram.isAutoGeneratedClassHour())
				classHourServiceImpl.classHourGen(academicProgram.getProgramId(), LocalDateTime.now());
		});
	}

	@Scheduled(fixedDelay = 300000L)
	public void updateClassStatus() {
		classHourServiceImpl.updaeClassStatus();	
	}

}
