package com.net.TeamCalen.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.net.TeamCalen.dao.ScheduleDao;
import com.net.TeamCalen.entity.Amount;
import com.net.TeamCalen.entity.Schedule;
@Service
public class ScheduleService {
	@Autowired
	ScheduleDao scheduleDao;
//	public void insertSchedule(int user_id,
//			Date date,
//			int start_hour,
//			int start_minute,
//			int end_hour,
//			int end_minute,
//			String schedule_text,
//			String state,
//			boolean hasReminder) {
//	   scheduleDao.insertSchedule(user_id, date, start_hour, start_minute, end_hour, end_minute, schedule_text, state,hasReminder);
//	}
	public void insertSchedule(Schedule schedule) {
		scheduleDao.insertSchedule(schedule);
	}
	public List<Schedule> selectSchedulebyuser_id(String user_id){
		
		return scheduleDao.selectSchedulebyuser_id(user_id);
	}
	public List<Map<String, Object>> selectSchedulebydate(Date date){
		return scheduleDao.selectSchedulebydate(date);
	}
	public List<Map<String, Object>> selectRecentSchedules(Date date,String order,int amount){
		return scheduleDao.selectRecentSchedules(date,order,amount);
	}
	public List<Amount> getEveryDayScheduleAmountInAMonth(String yearmonth){
		return scheduleDao.getEveryDayScheduleAmountInAMonth(yearmonth);
	}
	public Map<String, Object> selectSchedulebyscheduleid(int schedule_id) {
		return scheduleDao.selectSchedulebyscheduleid(schedule_id);
	}
	public boolean updateSchedulebystate(int schedule_id,String state) {
		return scheduleDao.updateSchedulebystate(schedule_id, state);
	}
	public boolean updateSchedule(Schedule schedule) {
		return scheduleDao.updateSchedule(schedule);
	}
	public boolean deleteSchedule(int schedule_id) {
		return scheduleDao.deleteSchedule(schedule_id);
	}
}
