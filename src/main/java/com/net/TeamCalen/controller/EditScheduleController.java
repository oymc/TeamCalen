package com.net.TeamCalen.controller;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.net.TeamCalen.entity.Schedule;
import com.net.TeamCalen.service.ScheduleService;
import com.net.TeamCalen.utils.JsonSet;

import net.minidev.json.JSONObject;

@Controller
public class EditScheduleController {
	@Autowired
	ScheduleService scheduleService;
	@PostMapping("controlPanel/createSchedule")
	@ResponseBody
	public JSONObject docreateSchedule(@RequestBody JSONObject jsonObject,HttpServletRequest request) {
		//userid从session中获取
		HttpSession session=request.getSession();
		int user_id=(int)session.getAttribute("user_id");
		String year=jsonObject.getAsString("year");
		String month=jsonObject.getAsString("month");
		String day=jsonObject.getAsString("day");
		//类型转换为'YYYY-MM-DD'
		String datestr=year+'-'+month+'-'+day;
		java.sql.Date date=Date.valueOf(datestr);
		int startHour=(int) jsonObject.getAsNumber("startHour");
		int startMinute=(int) jsonObject.getAsNumber("startMinute");
		int endHour=(int) jsonObject.getAsNumber("endHour");
		int endMinute=(int) jsonObject.getAsNumber("endMinute");
		String scheduleText=jsonObject.getAsString("scheduleText");
		boolean hasReminder=false;
		if(jsonObject.getAsString("hasReminder").equals("true")){
				hasReminder=true;
		}
		Schedule schedule=new Schedule(user_id,date,startHour,startMinute,endHour,endMinute,scheduleText,hasReminder);
		//定义要返回的json串
		JSONObject jsonReturn = new JSONObject();
	  if(scheduleService.insertSchedule(schedule)) {
		  jsonReturn=JsonSet.jsonReturnSet(200, null);
	   }
	   else{
		   jsonReturn=JsonSet.jsonReturnSet(400, null);
	   }
	  int scheduleId=schedule.getSchedule_id();
	  session.setAttribute("scheduleId", scheduleId);
	  return jsonReturn;
	}
	//切换日程是否完成
	@PostMapping("controlPanel/changeScheduleState")
	@ResponseBody
	public JSONObject dochangeScheduleState(@RequestBody  JSONObject jsonObject,HttpServletRequest request) {
		JSONObject jsonReturn=new JSONObject();
		HttpSession session=request.getSession();
		//判断操作权限
		int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
		if((int)session.getAttribute("user_id")!=scheduleService.judgeUserbyScheduleId(scheduleId)) {
			jsonReturn=JsonSet.jsonReturnSet(403, null);
			return jsonReturn;
		}
		String state=jsonObject.getAsString("state");
		String stateStr="";
		if(state.equals("true")) {
			stateStr="finished";
		}
		else {
			stateStr="unfinished";
		}
		if(scheduleService.updateSchedulebystate(scheduleId, stateStr)){
			jsonReturn=JsonSet.jsonReturnSet(200, null);
		}
		else {
			jsonReturn=JsonSet.jsonReturnSet(400, null);
		}
		return jsonReturn;
	}
	//恢复已经被取消的日程
	@PostMapping("controlPanel/resumeSchedule")
	@ResponseBody
	public JSONObject doresumeSchedule(@RequestBody  JSONObject jsonObject,HttpServletRequest request) {
		JSONObject jsonReturn=new JSONObject();
		HttpSession session=request.getSession();
		int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
		if((int)session.getAttribute("user_id")!=scheduleService.judgeUserbyScheduleId(scheduleId)) {
			jsonReturn=JsonSet.jsonReturnSet(403, null);
			return jsonReturn;
		}
		if(scheduleService.updateSchedulebystate(scheduleId, "unfinished")) {
			jsonReturn=JsonSet.jsonReturnSet(200, null);
		}
		else {
			jsonReturn=JsonSet.jsonReturnSet(400, null);
		}
		return jsonReturn;
	}
	//取消日程
		@PostMapping("controlPanel/cancelSchedule")
		@ResponseBody
		public JSONObject docancelSchedule(@RequestBody  JSONObject jsonObject,HttpServletRequest request) {
			JSONObject jsonReturn=new JSONObject();
			HttpSession session=request.getSession();
			int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
			if((int)session.getAttribute("user_id")!=scheduleService.judgeUserbyScheduleId(scheduleId)) {
				jsonReturn=JsonSet.jsonReturnSet(403, null);
				return jsonReturn;
			}
			if(scheduleService.updateSchedulebystate(scheduleId, "canceled")) {
				jsonReturn=JsonSet.jsonReturnSet(200, null);
			}
			else {
				jsonReturn=JsonSet.jsonReturnSet(400, null);
			}
			return jsonReturn;
		}
	//删除日程
	@PostMapping("controlPanel/deleteSchedule")
	@ResponseBody
	public JSONObject dodeleteSchedule(@RequestBody  JSONObject jsonObject,HttpServletRequest request) {
		JSONObject jsonReturn=new JSONObject();
		HttpSession session=request.getSession();
		int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
		if((int)session.getAttribute("user_id")!=scheduleService.judgeUserbyScheduleId(scheduleId)) {
			jsonReturn=JsonSet.jsonReturnSet(403, null);
			return jsonReturn;
		}
		if(scheduleService.deleteSchedule(scheduleId)) {
			jsonReturn=JsonSet.jsonReturnSet(200, null);
		}
		else {
			jsonReturn=JsonSet.jsonReturnSet(400, null);
		}
		return jsonReturn;
	}
	//编辑日程
	@PostMapping("controlPanel/modifySchedule")
	@ResponseBody
	public JSONObject domodifySchedule(@RequestBody  JSONObject jsonObject,HttpServletRequest request) {
		JSONObject jsonReturn=new JSONObject();
		HttpSession session=request.getSession();
		int user_id=(int) session.getAttribute("user_id");
		int scheduleId=(int) jsonObject.getAsNumber("id");
		if((int)session.getAttribute("user_id")!=scheduleService.judgeUserbyScheduleId(scheduleId)) {
			jsonReturn=JsonSet.jsonReturnSet(403, null);
			return jsonReturn;
		}
		String year=jsonObject.getAsString("year");
		String month=jsonObject.getAsString("month");
		String day=jsonObject.getAsString("day");
		//类型转换为'YYYY-MM-DD'
		String datestr=year+'-'+month+'-'+day;
		java.sql.Date date=Date.valueOf(datestr);
		int startHour=(int) jsonObject.getAsNumber("startHour");
		int startMinute=(int) jsonObject.getAsNumber("startMinute");
		int endHour=(int) jsonObject.getAsNumber("endHour");
		int endMinute=(int) jsonObject.getAsNumber("endMinute");
		String scheduleText=jsonObject.getAsString("scheduleText");
		System.out.println("modify"+jsonObject.getAsString("hasReminder"));
		boolean hasReminder=false;
		if(jsonObject.getAsString("hasReminder").equals("true")){
				hasReminder=true;
		}
		Schedule schedule=new Schedule(scheduleId,user_id,date, startHour, startMinute, endHour, endMinute, scheduleText,hasReminder);
		if(scheduleService.updateSchedule(schedule)) {
			jsonReturn=JsonSet.jsonReturnSet(200, null);
		}
		else {
			jsonReturn=JsonSet.jsonReturnSet(400, null);
		}
		return jsonReturn;
	}
}
