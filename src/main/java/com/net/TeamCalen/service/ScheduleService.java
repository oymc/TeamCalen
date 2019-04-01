package com.net.TeamCalen.service;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.net.TeamCalen.dao.ScheduleDao;
import com.net.TeamCalen.entity.Schedule;
@Service
public class ScheduleService {
	@Autowired
	ScheduleDao scheduleDao;
	public void insertSchedule(int user_id,
			Date date,
			int start_hour,
			int start_minute,
			int end_hour,
			int end_minute,
			String schedule_text,
			String schedule_state) {
	   scheduleDao.insertSchedule(user_id, date, start_hour, start_minute, end_hour, end_minute, schedule_text, schedule_state);
	}
	public List<Schedule> selectSchedulebyusername(String username){
		
		return scheduleDao.selectSchedulebyusername(username);
	}
	public List<Schedule> selectSchedulebydate(Date date){
		return scheduleDao.selectSchedulebydate(date);
	}
	public boolean updataSchedulebystate(int schedule_id,String state) {
		return scheduleDao.updataSchedulebystate(schedule_id, state);
	}
	public boolean updataSchedule(int schedule_id,Schedule schedule) {
		return scheduleDao.updataSchedule(schedule_id, schedule);
	}
	public boolean deleteSchedule(int schedule_id) {
		return scheduleDao.deleteSchedule(schedule_id);
	}
}
