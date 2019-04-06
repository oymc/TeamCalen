package com.net.TeamCalen.entity;
import java.sql.Date;

import org.junit.validator.PublicClassValidator;

import com.fasterxml.jackson.annotation.JsonFormat;
public class Schedule {
	private int schedule_id;
	private int user_id;
	//@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date date;
	private int start_hour;
	private int start_minute;
	private int end_hour;
	private int end_minute;
	private String schedule_text;
	private String state;
	private boolean hasReminder;
	public int getSchedule_id() {
		return schedule_id;
	}
	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	 @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getStart_hour() {
		return start_hour;
	}
	public void setStart_hour(int start_hour) {
		this.start_hour = start_hour;
	}
	public int getStart_minute() {
		return start_minute;
	}
	public void setStart_minute(int start_minute) {
		this.start_minute = start_minute;
	}
	public int getEnd_hour() {
		return end_hour;
	}
	public void setEnd_hour(int end_hour) {
		this.end_hour = end_hour;
	}
	public int getEnd_minute() {
		return end_minute;
	}
	public void setEnd_minute(int end_minute) {
		this.end_minute = end_minute;
	}
	public String getSchedule_text() {
		return schedule_text;
	}
	public void setSchedule_text(String schedule_text) {
		this.schedule_text = schedule_text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isHasReminder() {
		return hasReminder;
	}
	public void setHasReminder(boolean hasReminder) {
		this.hasReminder = hasReminder;
	}
	public Schedule() {}
	public Schedule(int user_id,Date date,int start_hour,int start_minute,int end_hour,int end_minute,String schedule_text,boolean hasReminder) {
		//this.schedule_id=0;
		this.user_id=user_id;
		this.date=date;
		this.start_hour=start_hour;
		this.start_minute=start_minute;
		this.end_hour=end_hour;
		this.end_minute=end_minute;
		this.schedule_text=schedule_text;
		this.state="unfinished";
		this.hasReminder=hasReminder;	
	}
	public Schedule(Date date,int start_hour,int start_minute,int end_hour,int end_minute,String schedule_text,boolean hasReminder) {
		this.date=date;
		this.start_hour=start_hour;
		this.start_minute=start_minute;
		this.end_hour=end_hour;
		this.end_minute=end_minute;
		this.schedule_text=schedule_text;
		this.state="unfinished";
		this.hasReminder=hasReminder;	
	}
	@Override
	public String toString() {
		return "Schedule{"+
	"schedule_id='"+schedule_id+'\''+
	",user_id='"+user_id+'\''+
	",date='"+date+'\''+
	",start_hour='"+start_hour+'\''+
	",start_minute'"+start_minute+'\''+
	",end_hour'"+end_hour+'\''+
	",end_minute'"+end_minute+'\''+
	"schedule_text'"+schedule_text+'\''+
	",state'"+state+'\''+
	",hasReminder'"+hasReminder+'\''+
	'}';
	}
}
