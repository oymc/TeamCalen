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
	public boolean insertSchedule(Schedule schedule) {
		return scheduleDao.insertSchedule(schedule);
	}
	public int judgeUserbyScheduleId(int schedule_id) {
		return scheduleDao.judgeUserbyScheduleId(schedule_id);
	}
	public List<Map<String, Object>> selectSchedulebyuser_id(int user_id){
		
		return scheduleDao.selectSchedulebyuser_id(user_id);
	}
	public List<Map<String, Object>> selectSchedulebydate(Date date,int user_id){
		return scheduleDao.selectSchedulebydate(date,user_id);
	}
	public List<Map<String, Object>> selectRecentSchedules(Date date,String order,int amount,int user_id ){
		return scheduleDao.selectRecentSchedules(date,order,amount,user_id);
	}
	public List<Amount> getEveryDayScheduleAmountInAMonth(String yearmonth,int user_id){
		return scheduleDao.getEveryDayScheduleAmountInAMonth(yearmonth,user_id);
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
