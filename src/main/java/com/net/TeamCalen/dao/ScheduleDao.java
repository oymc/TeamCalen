package com.net.TeamCalen.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.net.TeamCalen.entity.Schedule;

@Repository
public interface ScheduleDao {
	public void insertSchedule(
			@Param("user_id") int user_id,
			@Param("date") Date date,
			@Param("start_hour") int start_hour,
			@Param("start_minute") int start_minute,
			@Param("end_hour") int end_hour,
			@Param("end_minute") int end_minute,
			@Param("schedule_text") String schedule_text,
			@Param("schedule_state") String schedule_state,
			@Param("hasReminder") boolean hasReminder);
	public List<Schedule> selectSchedulebyuser_id(@Param("user_id") String user_id);
	public List<Schedule> selectSchedulebydate(@Param("date") Date date);
	/**
	 * 修改状态，从session获取id
	 * @param schedule_id
	 * @param state
	 * @return
	 */
	public boolean updataSchedulebystate(@Param("schedule_id") int schedule_id,@Param("state") String state);
	public boolean updataSchedule(@Param("schedule_id") int schedule_id,@Param("schedule") Schedule schedule);
	public boolean deleteSchedule(@Param("schedule_id") int schedule_id);
//	public Schedule selectSchedulenum(@Param("date") Date date);
}
