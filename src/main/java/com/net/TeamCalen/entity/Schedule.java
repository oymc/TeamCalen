package com.net.TeamCalen.entity;
import java.sql.Date;
public class Schedule {
	private int schedule_id;
	private int user_id;
	private Date date;
	private int start_hour;
	private int start_minute;
	private int end_hour;
	private int end_minute;
	private String schedule_text;
	private String schedule_state;
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
	public String getSchedule_state() {
		return schedule_state;
	}
	public void setSchedule_state(String schedule_state) {
		this.schedule_state = schedule_state;
	}
	public boolean isHasReminder() {
		return hasReminder;
	}
	public void setHasReminder(boolean hasReminder) {
		this.hasReminder = hasReminder;
	}
	
}
