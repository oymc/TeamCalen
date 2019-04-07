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
		JSONObject jsonObject2 = new JSONObject();
		Schedule schedule2=scheduleService.insertSchedule(schedule);
		int scheduleId=schedule2.getSchedule_id();
		session.setAttribute("scheduleId", scheduleId);
	  if(schedule!=null) {
		jsonObject2.put("code", 200);
		jsonObject2.put("data", null);
	   }
	   else{
		jsonObject2.put("code", 400);
		jsonObject2.put("data", null);
	   }
	   return jsonObject2;
	}
	//切换日程完成
	/**
	 * 这里的state用的string,记得改
	 * @param scheduleId
	 * @param state
	 * @return
	 */
	@PostMapping("controlPanel/changeScheduleState")
	@ResponseBody
	public JSONObject dochangeScheduleState(@RequestBody  JSONObject jsonObject) {
		int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
		String state=jsonObject.getAsString("state");
		String stateStr="";
		if(state.equals("true")) {
			stateStr="finished";
		}
		else {
			stateStr="unfinished";
		}
		JSONObject jsonObject2=new JSONObject();
		if(scheduleService.updateSchedulebystate(scheduleId, stateStr)) {
			jsonObject2.put("code", 200);
			jsonObject2.put("data", null);
		}
		else {
			jsonObject2.put("code", 400);
			jsonObject2.put("data", null);
		}
		return jsonObject2;
	}
	//恢复已经被取消的日程
	@PostMapping("controlPanel/resumeSchedule")
	@ResponseBody
	public JSONObject doresumeSchedule(@RequestBody  JSONObject jsonObject) {
		int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
		JSONObject jsonObject2=new JSONObject();
		if(scheduleService.updateSchedulebystate(scheduleId, "unfinished")) {
			jsonObject2.put("code", 200);
			jsonObject2.put("data", null);
		}
		else {
			jsonObject2.put("code", 400);
			jsonObject2.put("data", null);
		}
		return jsonObject2;
	}
	//取消日程
		@PostMapping("controlPanel/cancelSchedule")
		@ResponseBody
		public JSONObject docancelSchedule(@RequestBody  JSONObject jsonObject) {
			int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
			JSONObject jsonObject2=new JSONObject();
			if(scheduleService.updateSchedulebystate(scheduleId, "canceled")) {
				jsonObject2.put("code", 200);
				jsonObject2.put("data", null);
			}
			else {
				jsonObject2.put("code", 400);
				jsonObject2.put("data", null);
			}
			return jsonObject2;
		}
	//删除日程
	@PostMapping("controlPanel/deleteSchedule")
	@ResponseBody
	public JSONObject dodeleteSchedule(@RequestBody  JSONObject jsonObject) {
		int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
		JSONObject jsonObject2=new JSONObject();
		if(scheduleService.deleteSchedule(scheduleId)) {
			jsonObject2.put("code", 200);
			jsonObject2.put("data", null);
		}
		else {
			jsonObject2.put("code", 400);
			jsonObject2.put("data", null);
		}
		return jsonObject2;
	}
	//编辑日程
	@PostMapping("controlPanel/modifySchedule")
	@ResponseBody
	public JSONObject domodifySchedule(@RequestBody  JSONObject jsonObject) {
		int scheduleId=(int) jsonObject.getAsNumber("id");
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
		JSONObject jsonObject2=new JSONObject();
		if(scheduleService.updateSchedule(schedule)) {
			jsonObject2.put("code", 200);
			jsonObject2.put("data", null);
		}
		else {
			jsonObject2.put("code", 400);
			jsonObject2.put("data", null);
		}
		return jsonObject2;
	}
	//根据 ID 返回对应日程信息
	@GetMapping("controlPanel/getScheduleById")
	@ResponseBody
	public JSONObject dogetScheduleById(@RequestParam("scheduleId") int scheduleId) {
//		int scheduleId=(int) jsonObject.getAsNumber("scheduleId");
//		int scheduleId=schedule.getSchedule_id();
		JSONObject json=new JSONObject();
		Map<String, Object> map=scheduleService.selectSchedulebyscheduleid(scheduleId);
		json.put("code", 200);
		json.put("data", map);
		return json;
	}
	//得到某一天的所有日程
	@GetMapping("controlPanel/getSchedulesByDay")
	@ResponseBody
	public JSONObject getSchedulesByDay(@RequestParam("year") String year,@RequestParam("month") String month,@RequestParam("day") String day){
//		String year=jsonObject.getAsString("year");
//		String month=jsonObject.getAsString("month");
//		String day=jsonObject.getAsString("day");
		String datestr=year+'-'+month+'-'+day;
		java.sql.Date date=Date.valueOf(datestr);		
		List<Map<String, Object>> schedate=scheduleService.selectSchedulebydate(date);
		JSONObject json=new JSONObject();
		json.put("code", 200);
		json.put("data", schedate);
		return json;
	}
	//返回用户从今天起到未来的特定条日程
	@GetMapping("controlPanel/getRecentSchedules")
	@ResponseBody
	public JSONObject getRecentSchedules(@RequestParam("amount") int amount){
//		int amount=(int) jsonObject.getAsNumber("amount");
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		List<Map<String, Object>> schedate=scheduleService.selectRecentSchedules(date,"ASC",amount);
		JSONObject json=new JSONObject();
		json.put("code", 200);
		json.put("data", schedate);
		return json;
	}
	//获取指定年月中每一天的日程数量
	@GetMapping("controlPanel/getEveryDayScheduleAmountInAMonth")
	@ResponseBody
	public JSONObject getEveryDayScheduleAmountInAMonth(@RequestParam("year") String year,@RequestParam("month") String month) {
//		String year=jsonObject.getAsString("year");
//		String month=jsonObject.getAsString("month");
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
		List<Amount> list=scheduleService.getEveryDayScheduleAmountInAMonth(yearmonth);
		for(Amount m:list) {
			String day=m.getDay();
			int index=Integer.parseInt(day);
			int value=m.getScheduleAmount();
			scheduleAmount[index-1]=value;
	}
		for(int i=0;i<MaxDay;i++) {
			System.out.print(scheduleAmount[i]+'\n');
		}
		JSONObject json=new JSONObject();
		json.put("year", year);
		json.put("month", month);
		json.put("code", 200);
		json.put("data", scheduleAmount);
		return json;
	}
}
