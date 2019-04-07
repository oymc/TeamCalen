package com.net.TeamCalen.controller;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.sql.Date;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.coyote.http11.Http11AprProtocol;
import org.junit.validator.PublicClassValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitterReturnValueHandler;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers.CalendarDeserializer;
import com.net.TeamCalen.entity.Amount;
import com.net.TeamCalen.entity.Schedule;
import com.net.TeamCalen.service.ScheduleService;

import net.minidev.json.JSONObject;

@Controller
//@RequestMapping("controlPanel")
public class ScheduleController {
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
		JSONObject jsonNew = new JSONObject();
//		int scheduleId=schedule.getSchedule_id();
//		session.setAttribute("scheduleId", scheduleId);
	  if(scheduleService.insertSchedule(schedule)) {
		  jsonNew=this.setJson(200, null);
	   }
	   else{
		   jsonNew=this.setJson(400, null);
	   }
	  int scheduleId=schedule.getSchedule_id();
	  session.setAttribute("scheduleId", scheduleId);
	  return jsonNew;
	}
	//切换日程是否完成
	@PostMapping("controlPanel/changeScheduleState")
	@ResponseBody
	public JSONObject dochangeScheduleState(@RequestBody  JSONObject jsonObject,HttpServletRequest request) {
		JSONObject jsonNew=new JSONObject();
		HttpSession session=request.getSession();
		int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
		if(this.judgeUser((int)session.getAttribute("user_id"),scheduleId)) {
			jsonNew=this.setJson(403, null);
			return jsonNew;
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
			jsonNew=this.setJson(200, null);
		}
		else {
			jsonNew=this.setJson(400, null);
		}
		return jsonNew;
	}
	//恢复已经被取消的日程
	@PostMapping("controlPanel/resumeSchedule")
	@ResponseBody
	public JSONObject doresumeSchedule(@RequestBody  JSONObject jsonObject,HttpServletRequest request) {
		JSONObject jsonNew=new JSONObject();
		HttpSession session=request.getSession();
		int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
		if(this.judgeUser((int)session.getAttribute("user_id"),scheduleId)) {
			jsonNew=this.setJson(403, null);
			return jsonNew;
		}
		if(scheduleService.updateSchedulebystate(scheduleId, "unfinished")) {
			jsonNew=this.setJson(200, null);
		}
		else {
			jsonNew=this.setJson(400, null);
		}
		return jsonNew;
	}
	//取消日程
		@PostMapping("controlPanel/cancelSchedule")
		@ResponseBody
		public JSONObject docancelSchedule(@RequestBody  JSONObject jsonObject,HttpServletRequest request) {
			JSONObject jsonNew=new JSONObject();
			HttpSession session=request.getSession();
			int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
			if(this.judgeUser((int)session.getAttribute("user_id"),scheduleId)) {
				jsonNew=this.setJson(403, null);
				return jsonNew;
			}
			if(scheduleService.updateSchedulebystate(scheduleId, "canceled")) {
				jsonNew=this.setJson(200, null);
			}
			else {
				jsonNew=this.setJson(400, null);
			}
			return jsonNew;
		}
	//删除日程
	@PostMapping("controlPanel/deleteSchedule")
	@ResponseBody
	public JSONObject dodeleteSchedule(@RequestBody  JSONObject jsonObject,HttpServletRequest request) {
		JSONObject jsonNew=new JSONObject();
		HttpSession session=request.getSession();
		int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
		if(this.judgeUser((int)session.getAttribute("user_id"),scheduleId)) {
			jsonNew=this.setJson(403, null);
			return jsonNew;
		}
		if(scheduleService.deleteSchedule(scheduleId)) {
			jsonNew=this.setJson(200, null);
		}
		else {
			jsonNew=this.setJson(400, null);
		}
		return jsonNew;
	}
	//编辑日程
	@PostMapping("controlPanel/modifySchedule")
	@ResponseBody
	public JSONObject domodifySchedule(@RequestBody  JSONObject jsonObject,HttpServletRequest request) {
		JSONObject jsonNew=new JSONObject();
		HttpSession session=request.getSession();
		int scheduleId=(int) jsonObject.getAsNumber("id");
		if(this.judgeUser((int)session.getAttribute("user_id"),scheduleId)) {
			jsonNew=this.setJson(403, null);
			return jsonNew;
		}
		String year=jsonObject.getAsString("year");
		String month=jsonObject.getAsString("month");
		String day=jsonObject.getAsString("day");
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
		Schedule schedule=new Schedule(scheduleId,0,date, startHour, startMinute, endHour, endMinute, scheduleText,hasReminder);
		if(scheduleService.updateSchedule(schedule)) {
			jsonNew=this.setJson(200, null);
		}
		else {
			jsonNew=this.setJson(400, null);
		}
		return jsonNew;
	}
	//根据 ID 返回对应日程信息
	@GetMapping("controlPanel/getScheduleById")
	@ResponseBody
	public JSONObject dogetScheduleById(@RequestParam("scheduleId") int scheduleId,HttpServletRequest request) {
		JSONObject jsonNew=new JSONObject();
		HttpSession session=request.getSession();
		if(this.judgeUser((int)session.getAttribute("user_id"),scheduleId)) {
			jsonNew=this.setJson(403, null);
			return jsonNew;
		}
		Map<String, Object> map=scheduleService.selectSchedulebyscheduleid(scheduleId);
		if(map.get("hasReminder")=="1") {
			map.put("hasReminder", true);
		}
		else {
			map.put("hasReminder", false);
		}
		jsonNew=this.setJson(200,map);
		return jsonNew;
	}
	//得到某一天的所有日程
	@GetMapping("controlPanel/getSchedulesByDay")
	@ResponseBody
	public JSONObject getSchedulesByDay(@RequestParam("year") String year,@RequestParam("month") String month,@RequestParam("day") String day,HttpServletRequest request){
		HttpSession session=request.getSession();
		int user_id=(int) session.getAttribute("user_id");
		String datestr=year+'-'+month+'-'+day;
		java.sql.Date date=Date.valueOf(datestr);		
		List<Map<String, Object>> schedule=scheduleService.selectSchedulebydate(date,user_id);
		JSONObject jsonMap=new JSONObject();
		jsonMap.put("schedules", schedule);
		return this.setJson(200, jsonMap);
	}
	//返回用户从今天起到未来的特定条日程
	@GetMapping("controlPanel/getRecentSchedules")
	@ResponseBody
	public JSONObject getRecentSchedules(@RequestParam("amount") int amount,HttpServletRequest request){
//		int amount=(int) jsonObject.getAsNumber("amount");
		HttpSession session=request.getSession();
		int user_id=(int) session.getAttribute("user_id");
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		List<Map<String, Object>> schedule=scheduleService.selectRecentSchedules(date,"ASC",amount,user_id);
		JSONObject jsonList=new JSONObject();
		jsonList.put("schedules", schedule);
		return this.setJson(200, jsonList);
		
	}
	//TODO 获取指定年月中每一天的日程数量
	@GetMapping("controlPanel/getEveryDayScheduleAmountInAMonth")
	@ResponseBody
	public JSONObject getEveryDayScheduleAmountInAMonth(@RequestParam("year") String year,@RequestParam("month") String month,HttpServletRequest request) {
		HttpSession session=request.getSession();
		int user_id=(int) session.getAttribute("user_id");
		String yearmonth=year+'-'+month;
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyymm");
		try {
			calendar.setTime(simpleDateFormat.parse(year+month));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int MaxDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int[] scheduleAmount=new int[MaxDay];
		for(int i=0;i<MaxDay;i++) {
			scheduleAmount[i]=0;
		}
		List<Amount> list=scheduleService.getEveryDayScheduleAmountInAMonth(yearmonth,user_id);
		for(Amount m:list) {
			String day=m.getDay();
			int index=Integer.parseInt(day);
			int value=m.getScheduleAmount();
			scheduleAmount[index-1]=value;
	}
		for(int i=0;i<MaxDay;i++) {
			System.out.print(scheduleAmount[i]+'\n');
		}
		JSONObject jsonAmount=new JSONObject();
		jsonAmount.put("scheduleAmount", scheduleAmount);
		return this.setJson(200, jsonAmount);
	}
	//得到用户信息
	@GetMapping("controlPanel/getUserInfo")
	@ResponseBody
	public JSONObject getUserInfo(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String username=(String) session.getAttribute("username");
		JSONObject jsonName =new JSONObject();
		jsonName.put("username", username);
		return this.setJson(200, jsonName);
	}
	//根据传来的scheduleId和session，判断是否对用户有操作权限
	private boolean judgeUser(int user_id,int scheduleId) {
		System.out.println(user_id+scheduleId);
		if(user_id==scheduleService.judgeUserbyScheduleId(scheduleId)) 
			return false;
		else
			return true;
	}
	//请求状态
	private JSONObject setJson(int code,Object data) {
		JSONObject json=new JSONObject();
		json.put("code", code);
		json.put("data", data);
		return json;
	}
}
