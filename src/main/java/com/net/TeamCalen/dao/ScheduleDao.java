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
	/**
	 * 通过schedule_id判断操作是否合法
	 * @param schedule_id
	 * @return user_id
	 */
	public int judgeUserbyScheduleId(@Param("schedule_id") int schedule_id);
	/**
	 * 查询用户所有日程
	 * @param user_id
	 * @return
	 */
	public List<Map<String, Object>> selectSchedulebyuser_id(@Param("user_id") int user_id);
	/**
	 * 查询某用户某天的全部日程
	 * @param date
	 * @param user_id
	 * @return
	 */
	public List<Map<String, Object>> selectSchedulebydate(@Param("date") Date date,@Param("user_id") int user_id);
	/**
	 * 查询某条日程
	 * @param schedule_id
	 * @return schedule
	 */
	public Map<String, Object> selectSchedulebyscheduleid(@Param("schedule_id") int schedule_id);
	/**
	 * 
	 * @param date
	 * @param order 日期的排序方式 asc
	 * @param amount 条数
	 * @param user_id
	 * @return
	 */
	public List<Map<String, Object>> selectRecentSchedules(@Param("date") Date date,@Param("order") String order, @Param("amount") int amount,@Param("user_id") int user_id);
	/**
	 * 
	 * @param yearmonth :string 'YYYY-MM'
	 * @param user_id
	 * @return {Amount[day: ,ScheduleAmount:]} 自定义Amount类，每天对应的日程数量
	 */
	public List<Amount> getEveryDayScheduleAmountInAMonth(@Param("yearmonth") String yearmonth,@Param("user_id") int user_id);
	/**
	 * 修改状态
	 * @param schedule_id
	 * @param state
	 * @return
	 */
	public boolean updateSchedulebystate(@Param("schedule_id") int schedule_id,@Param("state") String state);
	/*
	 * 修改状态
	 */
	public boolean updateSchedule(@Param("schedule") Schedule schedule);
	/*
	 * 删除日程
	 */
	public boolean deleteSchedule(@Param("schedule_id") int schedule_id);
//	public Schedule selectSchedulenum(@Param("date") Date date);
}
