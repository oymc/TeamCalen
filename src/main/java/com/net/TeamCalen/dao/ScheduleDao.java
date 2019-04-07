package com.net.TeamCalen.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.net.TeamCalen.entity.Amount;
import com.net.TeamCalen.entity.Schedule;

@Repository
public interface ScheduleDao {
//	public void insertSchedule(
//			@Param("user_id") int user_id,
//			@Param("date") Date date,
//			@Param("start_hour") int start_hour,
//			@Param("start_minute") int start_minute,
//			@Param("end_hour") int end_hour,
//			@Param("end_minute") int end_minute,
//			@Param("schedule_text") String schedule_text,
//			@Param("state") String state,
//			@Param("hasReminder") boolean hasReminder);
	public boolean insertSchedule(Schedule schedule);
	public int judgeUserbyScheduleId(@Param("schedule_id") int schedule_id);
	public List<Map<String, Object>> selectSchedulebyuser_id(@Param("user_id") int user_id);
	public List<Map<String, Object>> selectSchedulebydate(@Param("date") Date date,@Param("user_id") int user_id);
	public Map<String, Object> selectSchedulebyscheduleid(@Param("schedule_id") int schedule_id);
	public List<Map<String, Object>> selectRecentSchedules(@Param("date") Date date,@Param("order") String order, @Param("amount") int amount,@Param("user_id") int user_id);
	public List<Amount> getEveryDayScheduleAmountInAMonth(@Param("yearmonth") String yearmonth,@Param("user_id") int user_id);
	/**
	 * 修改状态，从session获取id
	 * @param schedule_id
	 * @param state
	 * @return
	 */
	public boolean updateSchedulebystate(@Param("schedule_id") int schedule_id,@Param("state") String state);
	public boolean updateSchedule(@Param("schedule") Schedule schedule);
	public boolean deleteSchedule(@Param("schedule_id") int schedule_id);
//	public Schedule selectSchedulenum(@Param("date") Date date);
}
